package com.os.accounts.hollow

import com.netflix.hollow.api.producer.HollowProducer
import com.os.accounts.BaseAccountSpecification

class AccountProducerSpec extends BaseAccountSpecification {

    AccountProducer accountProducer = new AccountProducer()

    void setup() {
        GroovyMock(HollowProducer, global: true)
    }

    def 'save account'() {
        when: 'we save an account'
        accountProducer.save(account1)

        then: 'the producer runs a cycle'
        1 * accountProducer.producer.runCycle(_)
        0 * _
    }

    def 'saves accounts'() {
        when: 'we saves accounts'
        accountProducer.saveAccounts([account1, account2])

        then: 'the producer runs a cycle'
        1 * accountProducer.producer.runCycle(_)
        0 * _
    }
}
