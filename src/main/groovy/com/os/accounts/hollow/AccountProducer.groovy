package com.os.accounts.hollow

import com.netflix.hollow.api.producer.HollowIncrementalProducer
import com.netflix.hollow.api.producer.HollowProducer
import com.netflix.hollow.api.producer.fs.HollowFilesystemAnnouncer
import com.netflix.hollow.api.producer.fs.HollowFilesystemPublisher
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
    String hollowLocationPath

    @Value('${micronaut.application.hollow.account.version}')
    String hollowLocationVersion

    @Value('${micronaut.application.hollow.account.numStatesBetweenSnapshots:1}')
    Integer numStatesBetweenSnapshots

    Path localPublishDir
    HollowFilesystemPublisher publisher
    HollowFilesystemAnnouncer announcer
    HollowProducer producer
    HollowIncrementalProducer incrementalProducer
    boolean producerSetup = false

    void setupProducer() {
        if(!producerSetup)   {
            localPublishDir = new File("${hollowLocationPath}/${hollowLocationVersion}").toPath()
            publisher = new HollowFilesystemPublisher(localPublishDir)
            announcer = new HollowFilesystemAnnouncer(localPublishDir)
            producer = HollowProducer.withPublisher(publisher)
                    .withAnnouncer(announcer)
                    .withNumStatesBetweenSnapshots(numStatesBetweenSnapshots)
                    .withSnapshotPublishExecutor(Executors.newSingleThreadExecutor())
                    .withTargetMaxTypeShardSize(TYPE_SHARDING_SIZE)
                    .build()
            incrementalProducer = new HollowIncrementalProducer(producer)
            producerSetup = true
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

    void delete(Account account) {
        setupProducer()
        incrementalProducer.delete(account)
        long nextVersion = incrementalProducer.runCycle()
        log.debug("Deleted account with version: ${nextVersion}")
    }

}
