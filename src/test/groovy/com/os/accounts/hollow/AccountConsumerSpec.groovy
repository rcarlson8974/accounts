package com.os.accounts.hollow

import com.netflix.hollow.api.consumer.HollowConsumer
import com.os.accounts.BaseAccountSpecification
import com.os.accounts.hollow.domain.Account
import com.os.accounts.hollow.domain.AccountAPI
import com.os.accounts.hollow.domain.HString
import spock.lang.Ignore

class AccountConsumerSpec extends BaseAccountSpecification {

    HollowConsumer hollowConsumer = Mock()
    AccountAPI accountAPI = Mock()
    AccountConsumer accountConsumer = new AccountConsumer(hollowConsumer: hollowConsumer)

    @Ignore
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
        HString mockString = Mock()

        when:
        AccountConsumer.transformHollowAccounts([hollowAccount1, hollowAccount2])

        then:
        1 * hollowAccount1.version >> mockString
        1 * mockString.getValue() >> "verison1"
        1 * hollowAccount1.name >> mockString
        1 * mockString.getValue() >> "account1"
        1 * hollowAccount1.description >> mockString
        1 * mockString.getValue() >> "description1"
        1 * hollowAccount1.userId >> mockString
        1 * mockString.getValue() >> "userId1"
        1 * hollowAccount1.passwordHint >> mockString
        1 * mockString.getValue() >> "pwdHint1"
        1 * hollowAccount1.pinHint >> mockString
        1 * mockString.getValue() >> "pinHint1"
        1 * hollowAccount1.url >> mockString
        1 * mockString.getValue() >> "url1"
        1 * hollowAccount1.category >> mockString
        1 * mockString.getValue() >> "category1"
        1 * hollowAccount1.notes >> mockString
        1 * mockString.getValue() >> "notes1"

        1 * hollowAccount2.version >> mockString
        1 * mockString.getValue() >> "verison2"
        1 * hollowAccount2.name >> mockString
        1 * mockString.getValue() >> "account2"
        1 * hollowAccount2.description >> mockString
        1 * mockString.getValue() >> "description2"
        1 * hollowAccount2.userId >> mockString
        1 * mockString.getValue() >> "userId2"
        1 * hollowAccount2.passwordHint >> mockString
        1 * mockString.getValue() >> "pwdHint2"
        1 * hollowAccount2.pinHint >> mockString
        1 * mockString.getValue() >> "pinHint2"
        1 * hollowAccount2.url >> mockString
        1 * mockString.getValue() >> "url2"
        1 * hollowAccount2.category >> mockString
        1 * mockString.getValue() >> "category2"
        1 * hollowAccount2.notes >> mockString
        1 * mockString.getValue() >> "notes2"

        0 * _

    }
}
