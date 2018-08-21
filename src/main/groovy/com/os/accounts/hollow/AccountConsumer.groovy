package com.os.accounts.hollow

import com.netflix.hollow.api.consumer.HollowConsumer
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever
import com.os.accounts.domain.Account
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

    List<Account> findAllAccounts() {
        log.info 'Triggering hollow api refresh...'
        consumer.triggerRefresh()

        AccountAPI accountAPI = consumer.getAPI() as AccountAPI
        return transformToAccount(accountAPI.allAccount).sort { it.name }
    }

    static List<Account> transformToAccount(Collection<com.os.accounts.hollow.domain.Account> allAccounts) {
        def accounts = []
        allAccounts.each {
            accounts << new Account(
                    name: it.name.value,
                    description: it.description.value,
                    userId: it.userId.value,
                    passwordHint: it.passwordHint.value,
                    pinHint: it.pinHint.value,
                    url: it.url.value,
                    category: it.category.value,
                    notes: it.notes.value)
        }

        return accounts
    }

}
