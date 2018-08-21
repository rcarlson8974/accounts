package com.os.accounts.controller

import com.os.accounts.BaseAccountSpecification
import com.os.accounts.controller.AccountController
import com.os.accounts.service.AccountService
import io.micronaut.http.HttpStatus
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AccountControllerSpec extends BaseAccountSpecification {

    AccountService accountService = Mock()
    AccountController accountController = new AccountController(accountService: accountService)

    def 'list accounts'() {
        when:
        def response = accountController.list()

        then:
        1 * accountService.list() >> { [account1, account2] }
        response.status == HttpStatus.OK
        response.body() == [account1, account2]
    }

    def 'save account'() {
        when:
        def response = accountController.save(account1)

        then:
        1 * accountService.save(account1)
        response.status == HttpStatus.CREATED
        response.body() == account1
    }

    def 'save account handles exception being thrown'() {
        when:
        def response = accountController.save(account1)

        then:
        1 * accountService.save(account1) >> {
            throw new Exception("save account blew up")
        }
        0 * _
        response.status == HttpStatus.INTERNAL_SERVER_ERROR
        !response.body()
    }
}
