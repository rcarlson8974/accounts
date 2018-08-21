package com.os.accounts.hollow

import com.netflix.hollow.api.producer.HollowIncrementalProducer
import com.netflix.hollow.api.producer.HollowProducer
import com.netflix.hollow.api.producer.fs.HollowFilesystemAnnouncer
import com.netflix.hollow.api.producer.fs.HollowFilesystemPublisher
import com.os.accounts.domain.Account
import groovy.util.logging.Slf4j

import javax.inject.Singleton

@Singleton
@Slf4j
class AccountProducer {

    File localPublishDir = new File("/tmp/hollow")

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
            if(!it.name || it.name.isEmpty())   {
                it.name = it.description
            }
            incrementalProducer.addOrModify(it)
        }
        long nextVersion = incrementalProducer.runCycle()
        log.debug("Saved new accounts with version: ${nextVersion}")
    }
}
