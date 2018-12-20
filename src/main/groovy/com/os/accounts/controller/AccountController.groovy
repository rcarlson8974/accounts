package com.os.accounts.controller


import com.os.accounts.domain.Account
import com.os.accounts.service.AccountService
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated

import javax.inject.Inject

@Controller('/account/${micronaut.application.api.version}')
@Validated
@Slf4j
class AccountController {

    @Inject
    AccountService accountService

    @Get('/{accountId}')
    HttpResponse<Account> getAccount(String accountId) {
        try {
            def account = accountService.getAccount(accountId)
            return HttpResponse.ok(account)
        } catch (e) {
            String errMsg = "error trying to get account for accountId: ${accountId} -> ${e.message ?: e}"
            log.error(errMsg, e)
            return HttpResponse.serverError()
        }
    }

    @Get('/list')
    HttpResponse<List<Account>> list() {
        try {
            def accounts = accountService.list()
            return HttpResponse.ok(accounts)
        } catch (e) {
            String errMsg = "error trying to list accounts -> ${e.message ?: e}"
            log.error(errMsg, e)
            return HttpResponse.serverError()
        }
    }

    @Post("/save")
    HttpResponse<Account> save(@Body Account account) {
        try {
            accountService.save(account)
            return HttpResponse.created(account)
        } catch (e) {
            String errMsg = "error trying to save account ${account} -> ${e.message ?: e}"
            log.error(errMsg, e)
            return HttpResponse.serverError()
        }
    }

    @Post("/save-all")
    HttpResponse<List<Account>> saveAccounts(@Body List<Account> accounts) {
        try {
            accountService.saveAccounts(accounts)
            return HttpResponse.created(accounts)
        } catch (e) {
            String errMsg = "error trying to save accounts ${accounts} -> ${e.message ?: e}"
            log.error(errMsg, e)
            return HttpResponse.serverError()
        }
    }

    @Delete('/{accountId}')
    HttpResponse delete(String accountId) {
        try {
            accountService.delete(accountId)
            return HttpResponse.ok(accountId)
        } catch (e) {
            String errMsg = "error trying to delete account ${accountId} -> ${e.message ?: e}"
            log.error(errMsg, e)
            return HttpResponse.serverError()
        }
    }
}