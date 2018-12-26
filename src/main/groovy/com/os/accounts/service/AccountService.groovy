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

    Account getAccount(String accountName) {
        return accountConsumer.findAccount(accountName)
    }

    List<Account> list() {
        return accountConsumer.findAllAccounts()
    }

    void save(Account account) {
        accountProducer.save(account)
    }

    void saveAccounts(List<Account> accounts) {
        accountProducer.saveAccounts(accounts)
    }

    void delete(String accountName) {
        def account = getAccount(accountName)
//        accountProducer.delete(accountName)
        accountProducer.delete(account)
    }


}
