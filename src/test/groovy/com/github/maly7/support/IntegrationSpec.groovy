package com.github.maly7.support

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@TestPropertySource('classpath:test.properties')
@SpringBootTest
class IntegrationSpec extends Specification {

}
