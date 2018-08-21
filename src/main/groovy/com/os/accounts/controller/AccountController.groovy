package com.os.accounts.controller

import com.netflix.hollow.api.codegen.HollowAPIGenerator
import com.netflix.hollow.core.write.HollowWriteStateEngine
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper
import com.os.accounts.domain.Account
import com.os.accounts.hollow.AccountGenerator
import com.os.accounts.service.AccountService
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

import javax.inject.Inject

@Controller('/${accounts.api.version}')
@Slf4j
class AccountController {

    @Inject
    AccountService accountService

    @Get("/accounts/generate")
    HttpResponse generate() {
        AccountGenerator.generate()
        return HttpResponse
                .ok()
                .body(api_generated: 'true')
    }

    @Get("/accounts/list")
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

    @Post("/account/save")
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

    @Post("/accounts/save")
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

    @Delete("/account/delete")
    HttpResponse<Account> delete(@Body Account account) {
        try {
            accountService.delete(account)
            return HttpResponse.ok(account)
        } catch (e) {
            String errMsg = "error trying to delete account ${account} -> ${e.message ?: e}"
            log.error(errMsg, e)
            return HttpResponse.serverError()
        }
    }


}