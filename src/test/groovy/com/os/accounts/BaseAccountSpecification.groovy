package com.os.accounts

import com.os.accounts.domain.Account
import spock.lang.Shared
import spock.lang.Specification

class BaseAccountSpecification extends Specification {

    @Shared
    def account1 = new Account(
            version: 'version1',
            name: 'account1',
            description: 'description1',
            userId: 'userId1',
            passwordHint: 'pwdHint1',
            pinHint: 'pinHint1',
            url: 'url1',
            category: 'category1',
            notes: 'notes1',
    )

    @Shared
    def account2 = new Account(
            version: 'version2',
            name: 'account2',
            description: 'description2',
            userId: 'userId2',
            passwordHint: 'pwdHint2',
            pinHint: 'pinHint2',
            url: 'url2',
            category: 'category2',
            notes: 'notes2'
    )

}
