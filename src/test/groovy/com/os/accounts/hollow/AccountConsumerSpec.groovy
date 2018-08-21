package com.os.accounts.hollow

import com.netflix.hollow.api.consumer.HollowConsumer
import com.os.accounts.BaseAccountSpecification
import com.os.accounts.hollow.domain.Account
import com.os.accounts.hollow.domain.AccountAPI

class AccountConsumerSpec extends BaseAccountSpecification {

    HollowConsumer hollowConsumer = Mock()
    AccountAPI accountAPI = Mock()
    AccountConsumer accountConsumer = new AccountConsumer(consumer: hollowConsumer)

    def 'finds all accounts and sorts by name'() {
        when:
        def accounts = accountConsumer.findAllAccounts()

        then:
        1 * hollowConsumer.triggerRefresh()
        1 * hollowConsumer.getAPI() >> accountAPI
        1 * accountAPI.getAllAccount() >> {
            [account1, account2]
        }
        0 * _
        accounts == [account1, account2]

    }

    def 'transforms hollow objects to domain objects'() {
        setup:
        Account hollowAccount1 = Mock()
        Account hollowAccount2 = Mock()

        when:
        AccountConsumer.transformToAccount([hollowAccount1, hollowAccount2])

        then:
        1 * hollowAccount1.name >> mockString
        1 * hollowAccount1.description >> mockString
        1 * hollowAccount1.userId >> mockString
        1 * hollowAccount1.passwordHint >> mockString
        1 * hollowAccount1.pinHint >> mockString
        1 * hollowAccount1.url >> mockString
        1 * hollowAccount1.category >> mockString
        1 * hollowAccount1.notes >> mockString

        1 * hollowAccount2.name >> mockString
        1 * hollowAccount2.description >> mockString
        1 * hollowAccount2.userId >> mockString
        1 * hollowAccount2.passwordHint >> mockString
        1 * hollowAccount2.pinHint >> mockString
        1 * hollowAccount2.url >> mockString
        1 * hollowAccount2.category >> mockString
        1 * hollowAccount2.notes >> mockString

        0 * _

    }
}
