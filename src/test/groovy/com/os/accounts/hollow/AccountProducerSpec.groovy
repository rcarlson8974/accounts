package com.os.accounts.hollow

import com.netflix.hollow.api.producer.HollowIncrementalProducer
import com.netflix.hollow.api.producer.HollowProducer
import com.os.accounts.BaseAccountSpecification
import spock.lang.Ignore

@Ignore
class AccountProducerSpec extends BaseAccountSpecification {

    HollowIncrementalProducer incrementalProducer = Mock()
    AccountProducer accountProducer = new AccountProducer(hollowLocationPath: '/tmp/hollow/test/account', hollowLocationVersion: 'v1', numStatesBetweenSnapshots: 1, incrementalProducer: incrementalProducer)

    void setup() {
        GroovyMock(HollowProducer, global: true)
    }

    def 'save account'() {
        when: 'we save an account'
        accountProducer.save(account1)

        then: 'the producer runs a cycle'
        1 * incrementalProducer.addOrModify(account1)
        1 * incrementalProducer.runCycle()
        0 * _
    }

    def 'saves accounts'() {
        when: 'we save accounts'
        accountProducer.saveAccounts([account1, account2])

        then: 'the producer adds both accounts, then runs a cycle'
        1 * incrementalProducer.addOrModify(account1)
        1 * incrementalProducer.addOrModify(account2)
        1 * incrementalProducer.runCycle()
        0 * _
    }

    def 'delete account'() {
        when: 'we delete an account'
        accountProducer.delete(account1)

        then: 'the producer runs a cycle'
        1 * incrementalProducer.delete(account1)
        1 * incrementalProducer.runCycle()
        0 * _
    }
}
