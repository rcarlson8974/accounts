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

@Singleton
@Slf4j
class AccountProducer {

    @Value('${micronaut.application.hollow.account.path}')
    String hollowLocationPath

    @Value('${micronaut.application.hollow.account.version}')
    String hollowLocationVersion

    Path localPublishDir = new File("${hollowLocationPath}/${hollowLocationVersion}").toPath()

    HollowFilesystemPublisher publisher = new HollowFilesystemPublisher(localPublishDir)
    HollowFilesystemAnnouncer announcer = new HollowFilesystemAnnouncer(localPublishDir)

    HollowProducer producer = HollowProducer.withPublisher(publisher)
            .withAnnouncer(announcer)
            .build()
    HollowIncrementalProducer incrementalProducer = new HollowIncrementalProducer(producer)

    void save(Account account) {
        incrementalProducer.addOrModify(account)
        long nextVersion = incrementalProducer.runCycle()
        log.debug("Saved new account with version: ${nextVersion}")
    }

    void saveAccounts(List<Account> accounts) {
        accounts.each {
            incrementalProducer.addOrModify(it)
        }
        long nextVersion = incrementalProducer.runCycle()
        log.debug("Saved new accounts with version: ${nextVersion}")
    }

    void delete(Account account) {
        incrementalProducer.delete(account)
        long nextVersion = incrementalProducer.runCycle()
        log.debug("Deleted account with version: ${nextVersion}")
    }

}
