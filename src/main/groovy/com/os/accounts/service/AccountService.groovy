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
        return accountConsumer.findAllAccounts()
    }

    void save(Account account) {
        accountProducer.save(account)
    }

    void saveAccounts(List<Account> accounts) {
        accountProducer.saveAccounts(accounts)
    }
}
