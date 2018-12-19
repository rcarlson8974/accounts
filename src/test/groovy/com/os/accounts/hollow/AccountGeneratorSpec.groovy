package com.os.accounts.hollow

import spock.lang.Ignore
import spock.lang.Specification

class AccountGeneratorSpec extends Specification {

    @Ignore
    def 'generates hollow api'() {
        when:
        AccountGenerator.generate()

        then:
        1 == 1
    }

}
