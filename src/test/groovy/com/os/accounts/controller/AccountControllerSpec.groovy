package com.os.accounts.controller

import com.netflix.hollow.api.producer.HollowProducer
import com.os.accounts.BaseAccountSpecification
import com.os.accounts.hollow.AccountGenerator
import com.os.accounts.service.AccountService
import io.micronaut.http.HttpStatus
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AccountControllerSpec extends BaseAccountSpecification {

    AccountService accountService = Mock()
    AccountController accountController = new AccountController(accountService: accountService)

    void setup() {
        GroovyMock(AccountGenerator, global: true)
    }

    def 'generates new hollow domain objects'() {
        when:
        def response = accountController.generate()

        then:
        1 * AccountGenerator.generate()
        0 * _
        response.status == HttpStatus.OK
        response.body() == [api_generated: 'true']
    }

    def 'list accounts'() {
        when:
        def response = accountController.list()

        then:
        1 * accountService.list() >> { [account1, account2] }
        0 * _
        response.status == HttpStatus.OK
        response.body() == [account1, account2]
    }

    def 'list accounts handles exception being thrown'() {
        when:
        def response = accountController.list()

        then:
        1 * accountService.list() >> {
            throw new Exception("list accounts blew up")
        }
        0 * _
        response.status == HttpStatus.INTERNAL_SERVER_ERROR
        !response.body()
    }

    def 'save account'() {
        when:
        def response = accountController.save(account1)

        then:
        1 * accountService.save(account1)
        0 * _
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

    def 'save accounts'() {
        when:
        def response = accountController.saveAccounts([account1, account2])

        then:
        1 * accountService.saveAccounts([account1, account2])
        0 * _
        response.status == HttpStatus.CREATED
        response.body() == [account1, account2]
    }

    def 'save accounts handles exception being thrown'() {
        when:
        def response = accountController.saveAccounts([account1, account2])

        then:
        1 * accountService.saveAccounts([account1, account2]) >> {
            throw new Exception("save accounts blew up")
        }
        0 * _
        response.status == HttpStatus.INTERNAL_SERVER_ERROR
        !response.body()
    }

    def 'delete account'() {
        when:
        def response = accountController.delete(account1)

        then:
        1 * accountService.delete(account1)
        0 * _
        response.status == HttpStatus.OK
        response.body() == account1
    }

    def 'delete account handles exception being thrown'() {
        when:
        def response = accountController.delete(account1)

        then:
        1 * accountService.delete(account1) >> {
            throw new Exception("delete account blew up")
        }
        0 * _
        response.status == HttpStatus.INTERNAL_SERVER_ERROR
        !response.body()
    }

}
