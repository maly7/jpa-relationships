package com.github.maly7.support

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class IntegrationSpec extends Specification {

    void 'startup works'() {
        expect:
        true
    }

}
