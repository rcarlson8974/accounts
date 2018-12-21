package com.os.accounts.hollow

import com.netflix.hollow.api.consumer.HollowConsumer
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever
import com.os.accounts.domain.Account
import com.os.accounts.hollow.domain.AccountAPI
import com.os.accounts.hollow.domain.AccountPrimaryKeyIndex
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Value

import javax.inject.Singleton
import java.nio.file.Path

@Singleton
@Slf4j
class AccountConsumer {

    @Value('${micronaut.application.hollow.account.path}')
    String hollowLocationPath

    @Value('${micronaut.application.hollow.account.version}')
    String hollowLocationVersion

    Path localPublishDir
    HollowFilesystemBlobRetriever blobRetriever
    HollowFilesystemAnnouncementWatcher announcementWatcher
    HollowConsumer hollowConsumer
    AccountPrimaryKeyIndex accountPrimaryKeyIndex
    boolean indexReady = false
    boolean consumerReady = false

    void setupConsumer() {

        if (!consumerReady) {
            localPublishDir = new File("${hollowLocationPath}/${hollowLocationVersion}").toPath()
            blobRetriever = new HollowFilesystemBlobRetriever(localPublishDir)
            announcementWatcher = new HollowFilesystemAnnouncementWatcher(localPublishDir)
            hollowConsumer = HollowConsumer.withBlobRetriever(blobRetriever)
                    .withAnnouncementWatcher(announcementWatcher)
                    .withGeneratedAPIClass(AccountAPI)
                    .build()
            log.info 'Triggering hollow api refresh...'
            hollowConsumer.triggerRefresh()
            consumerReady = true
        }

        while (!indexReady) {
            try {
                accountPrimaryKeyIndex = new AccountPrimaryKeyIndex(hollowConsumer)
                accountPrimaryKeyIndex.listenToDataRefresh()
                indexReady = true
                log.info("index ${accountPrimaryKeyIndex} is ready")
            } catch (Exception e) {
                log.info("index ${accountPrimaryKeyIndex} is not ready.  sleeping for 1s")
                sleep(1000)
            }
        }
    }

    Account findAccount(String accountId) {
        setupConsumer()
        def hollowAccount = accountPrimaryKeyIndex.findMatch(accountId)
        return hollowAccount ? transformHollowAccount(hollowAccount) : null
    }

    List<Account> findAllAccounts() {
        setupConsumer()
        AccountAPI accountAPI = hollowConsumer.getAPI() as AccountAPI
        return transformHollowAccounts(accountAPI.allAccount).sort { it.name }
    }

    static List<Account> transformHollowAccounts(Collection<com.os.accounts.hollow.domain.Account> allAccounts) {
        def accounts = []
        allAccounts.each {
            accounts << transformHollowAccount(it)
        }

        return accounts
    }

    static Account transformHollowAccount(com.os.accounts.hollow.domain.Account hollowAccount) {
        return new Account(
                version: hollowAccount.version.value,
                name: hollowAccount.name.value,
                description: hollowAccount.description.value,
                userId: hollowAccount.userId?.value,
                passwordHint: hollowAccount.passwordHint?.value,
                pinHint: hollowAccount.pinHint?.value,
                url: hollowAccount.url?.value,
                category: hollowAccount.category?.value,
                notes: hollowAccount.notes?.value)
    }

}
