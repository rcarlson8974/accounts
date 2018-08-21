package com.os.accounts

import com.os.accounts.domain.Account
import spock.lang.Shared
import spock.lang.Specification

class BaseAccountSpecification extends Specification {

    @Shared
    def account1 = new Account(name: 'acc1')

    @Shared
    def account2 = new Account(name: 'acc2')
}
