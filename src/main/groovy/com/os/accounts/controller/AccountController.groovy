package com.os.accounts.controller

import com.netflix.hollow.api.codegen.HollowAPIGenerator
import com.netflix.hollow.core.write.HollowWriteStateEngine
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper
import com.os.accounts.domain.Account
import com.os.accounts.service.AccountService
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
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

        log.debug("Generating hollow API")
        try {

            HollowWriteStateEngine writeEngine = new HollowWriteStateEngine()
            HollowObjectMapper mapper = new HollowObjectMapper(writeEngine)
            mapper.initializeTypeState(Account)

            HollowAPIGenerator generator = new HollowAPIGenerator.Builder()
                    .withAPIClassname("AccountAPI")
                    .withDestination("//Users/z001mvb/projects/os/accounts/src/main/groovy/com/os/accounts/hollow/domain")
                    .withPackageName("com.os.accounts.hollow.domain")
                    .withDataModel(writeEngine)
                    .build()
            generator.generateSourceFiles()

            return HttpResponse
                    .ok()
                    .body(api_generated: 'true')
        } catch (e) {
            String errMsg = "error trying to generate hollow api -> ${e.message ?: e}"
            log.error(errMsg, e)
            return HttpResponse.serverError()
        }
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
}