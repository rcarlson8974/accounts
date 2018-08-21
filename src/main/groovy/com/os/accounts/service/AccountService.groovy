package com.os.accounts.service


import com.os.accounts.domain.Account
import com.os.accounts.hollow.AccountConsumer
import com.os.accounts.hollow.AccountProducer

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountService {

    @Inject
    AccountProducer accountProducer

    @Inject
    AccountConsumer accountConsumer

    List<Account> list() {
        def allAccounts = accountConsumer.findAllAccounts()
        return transformToAccount(allAccounts)
    }

    void save(Account account) {
        accountProducer.save(account)
    }

    void saveAccounts(List<Account> accounts) {
        accountProducer.saveAccounts(accounts)
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
                    notes: it.notes.value)
        }

        return accounts
    }
}
