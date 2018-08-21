package com.os.accounts.hollow

import com.netflix.hollow.api.consumer.HollowConsumer
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever
import com.os.accounts.hollow.domain.Account
import com.os.accounts.hollow.domain.AccountAPI
import groovy.util.logging.Slf4j

import javax.inject.Singleton

@Singleton
@Slf4j
class AccountConsumer {

    File localPublishDir = new File("/tmp/hollow")

    HollowFilesystemBlobRetriever blobRetriever = new HollowFilesystemBlobRetriever(localPublishDir)
    HollowFilesystemAnnouncementWatcher announcementWatcher = new HollowFilesystemAnnouncementWatcher(localPublishDir)
    HollowConsumer consumer = HollowConsumer.withBlobRetriever(blobRetriever)
            .withAnnouncementWatcher(announcementWatcher)
            .withGeneratedAPIClass(AccountAPI)
            .build()

    Collection<Account> findAllAccounts() {
        log.info 'Triggering hollow api refresh...'
        consumer.triggerRefresh()

        AccountAPI accountAPI = consumer.getAPI() as AccountAPI
        return accountAPI.allAccount
    }

}
