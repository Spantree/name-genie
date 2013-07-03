package net.spantree.namegenie

import org.slf4j.LoggerFactory
import spock.lang.Specification

import java.nio.ByteBuffer

class NameGenieSpec extends Specification {
    static final int TEST_RUNS = 1000000

    def log = LoggerFactory.getLogger(getClass())
    def genie = new NameGenie()

    def "should generate lots of names with no collisions"() {
        setup:
        Set<String> names = []
        Set<ByteBuffer> hashes = []
        int femaleCount = 0
        int maleCount = 0

        expect: "there will be no name or hash collisions for genetrated names"
        for(int i=0; i<TEST_RUNS; i++) {
            Person p
            if(gender == null) {
                p = genie.generate()
            } else if(gender == Gender.Female) {
                p = genie.generateFemale()
            } else {
                p = genie.generateMale()
            }
            log.info "Generated ${p.gender} Name:\t${p.firstName} ${p.lastName}"
            assert names.add("${p.firstName} ${p.lastName}")
            assert hashes.add(p.sha1NameHash)
            if(gender != null) {
                assert p.gender == gender
            }
            if(p.gender == Gender.Male) maleCount++ else femaleCount++
        }

        and: "if generating for both genders, we should have a relatively even gender distribution"
        if(gender == null) {
            assert Math.abs(femaleCount - maleCount) < TEST_RUNS / 100
        }

        where:
        gender << [null, Gender.Female, Gender.Male]
    }
}
