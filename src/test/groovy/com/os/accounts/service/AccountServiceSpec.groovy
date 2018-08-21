package com.os.accounts.service

import com.os.accounts.BaseAccountSpecification
import com.os.accounts.hollow.AccountProducer
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AccountServiceSpec extends BaseAccountSpecification {

//    AccountProducer accountProducer = Mock()
//    AccountService accountService = new AccountService(accountProducer: accountProducer)
//
//    def 'save account'() {
//        when:
//        accountService.save(account1)
//
//        then:
//        1 * accountProducer.save(account1)
//    }
//
//    def 'save account handles exception being thrown'() {
//        when:
//        accountService.save(account1)
//
//        then:
//        1 * accountProducer.save(account1) >> {
//            throw new Exception("save account blew up")
//        }
//        0 * _
//        Exception ex = thrown()
//        ex.message == 'save account blew up'
//    }

}
