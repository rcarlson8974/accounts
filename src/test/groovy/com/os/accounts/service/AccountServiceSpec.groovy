package com.os.accounts.service

import com.os.accounts.BaseAccountSpecification
import com.os.accounts.hollow.AccountConsumer
import com.os.accounts.hollow.AccountProducer
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AccountServiceSpec extends BaseAccountSpecification {

    AccountProducer accountProducer = Mock()
    AccountConsumer accountConsumer = Mock()
    AccountService accountService = new AccountService(accountProducer: accountProducer, accountConsumer: accountConsumer)

    def 'list accounts'() {
        when:
        def accounts = accountService.list()

        then:
        1 * accountConsumer.findAllAccounts() >> {
            [account1, account2]
        }
        0 * _
        accounts == [account1, account2]
    }

    def 'list accounts handles exception being thrown'() {
        when:
        accountService.list()

        then:
        1 * accountConsumer.findAllAccounts() >> {
            throw new Exception("list accounts blew up")
        }
        0 * _
        Exception ex = thrown()
        ex.message == 'list accounts blew up'
    }

    def 'save account'() {
        when:
        accountService.save(account1)

        then:
        1 * accountProducer.save(account1)
        0 * _
    }

    def 'save account handles exception being thrown'() {
        when:
        accountService.save(account1)

        then:
        1 * accountProducer.save(account1) >> {
            throw new Exception("save account blew up")
        }
        0 * _
        Exception ex = thrown()
        ex.message == 'save account blew up'
    }

    def 'save accounts'() {
        when:
        accountService.saveAccounts([account1, account2])

        then:
        1 * accountProducer.saveAccounts([account1, account2])
        0 * _
    }

    def 'save accounts handles exception being thrown'() {
        when:
        accountService.saveAccounts([account1, account2])

        then:
        1 * accountProducer.saveAccounts([account1, account2]) >> {
            throw new Exception("save accounts blew up")
        }
        0 * _
        Exception ex = thrown()
        ex.message == 'save accounts blew up'
    }

}
