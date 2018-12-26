package com.os.accounts.hollow

import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever
import com.netflix.hollow.api.producer.HollowIncrementalProducer
import com.netflix.hollow.api.producer.HollowProducer
import com.netflix.hollow.api.producer.fs.HollowFilesystemAnnouncer
import com.netflix.hollow.api.producer.fs.HollowFilesystemPublisher
import com.netflix.hollow.core.write.objectmapper.RecordPrimaryKey
import com.os.accounts.domain.Account
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Value

import javax.inject.Singleton
import java.nio.file.Path
import java.util.concurrent.Executors

@Singleton
@Slf4j
class AccountProducer {

    private static final long TYPE_SHARDING_SIZE = 25 * 1024 * 1024 //25 MB

    @Value('${micronaut.application.hollow.account.path}')
    String hollowAccountPath

    @Value('${micronaut.application.hollow.account.version}')
    String hollowAccountVersion

    @Value('${micronaut.application.hollow.account.numStatesBetweenSnapshots:1}')
    Integer numStatesBetweenSnapshots

    Path localPublishDir
    HollowFilesystemPublisher publisher
    HollowFilesystemAnnouncer announcer
    HollowFilesystemAnnouncementWatcher announcementWatcher
    HollowFilesystemBlobRetriever blobRetriever
    HollowProducer producer
    HollowIncrementalProducer incrementalProducer
    boolean producerSetup = false

    void setupProducer() {
        if (!producerSetup) {
            localPublishDir = new File("${hollowAccountPath}/${hollowAccountVersion}").toPath()
            publisher = new HollowFilesystemPublisher(localPublishDir)
            announcer = new HollowFilesystemAnnouncer(localPublishDir)
            announcementWatcher = new HollowFilesystemAnnouncementWatcher(localPublishDir)
            blobRetriever = new HollowFilesystemBlobRetriever(localPublishDir)
            producer = HollowProducer.withPublisher(publisher)
                    .withAnnouncer(announcer)
                    .withNumStatesBetweenSnapshots(numStatesBetweenSnapshots)
                    .withSnapshotPublishExecutor(Executors.newSingleThreadExecutor())
                    .withTargetMaxTypeShardSize(TYPE_SHARDING_SIZE)
                    .build()
            restoreFromExistingState()
            incrementalProducer = new HollowIncrementalProducer(producer)
            producerSetup = true
        }

    }

    void restoreFromExistingState() {
        long latestAnnouncedVersion
        try {
            producer.initializeDataModel(Account)
            latestAnnouncedVersion = announcementWatcher.latestVersion
            if (latestAnnouncedVersion == 0L || latestAnnouncedVersion == HollowFilesystemAnnouncementWatcher.NO_ANNOUNCEMENT_AVAILABLE) {
                log.info("First execution with latest announced version is $latestAnnouncedVersion")
//                firstExecution = true
                return
            }
        } catch (Exception e) {
            log.error("Previous state not available", e)
            throw e
        }

        try {
            producer.initializeDataModel(Account)
            producer.restore(latestAnnouncedVersion, blobRetriever)
        } catch (Exception e) {
            log.error("could not restore from previous state!", e)
            throw e
        }
    }

    void save(Account account) {
        setupProducer()
        incrementalProducer.addOrModify(account)
        long nextVersion = incrementalProducer.runCycle()
        log.debug("Saved new account with version: ${nextVersion}")
    }

    void saveAccounts(List<Account> accounts) {
        setupProducer()
        accounts.each {
            incrementalProducer.addOrModify(it)
        }
        long nextVersion = incrementalProducer.runCycle()
        log.debug("Saved new accounts with version: ${nextVersion}")
    }

    void delete(String accountName) {
        setupProducer()
        incrementalProducer.delete(new RecordPrimaryKey("String", accountName))
        incrementalProducer.runCycle()
        log.debug("Deleted account with name: ${accountName}")
    }

    void delete(Account account) {
        setupProducer()
        incrementalProducer.delete(account)
        incrementalProducer.runCycle()
        log.debug("Deleted account: ${account}")
    }

}
