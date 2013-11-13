package net.spantree.namegenius

import org.slf4j.LoggerFactory
import spock.lang.Specification

import java.nio.ByteBuffer

class NameGeniusSpec extends Specification {
    static final int TEST_RUNS = 1000000

    def log = LoggerFactory.getLogger(getClass())
    def genius = new NameGenius()

    def "should generate lots of names with no collisions"() {
        setup:
        Set<String> names = []
        Set<ByteBuffer> hashes = []
        int femaleCount = 0
        int maleCount = 0

        expect: "there will be no name or hash collisions for genetrated names"
        for (int i = 0; i < TEST_RUNS; i++) {
            Person p
            if (gender == null) {
                p = genius.generate()
            } else if (gender == Gender.Female) {
                p = genius.generateFemale()
            } else {
                p = genius.generateMale()
            }
            log.info "Generated ${p.gender} Name:\t${p.firstName} ${p.lastName}"
            assert names.add("${p.firstName} ${p.lastName}")
            assert hashes.add(p.sha1NameHash)
            if (gender != null) {
                assert p.gender == gender
            }
            if (p.gender == Gender.Male) maleCount++ else femaleCount++
        }

        and: "if generating for both genders, we should have a relatively even gender distribution"
        if (gender == null) {
            assert Math.abs(femaleCount - maleCount) < TEST_RUNS / 100
        }

        where:
        gender << [null, Gender.Female, Gender.Male]
    }

    def "should generate lots of employees with no collisions"() {
        setup:
        Set<String> names = []
        Set<ByteBuffer> hashes = []
        int femaleCount = 0
        int maleCount = 0

        expect: "there will be no name or hash collisions for genetrated names"
        for (int i = 0; i < TEST_RUNS; i++) {
            Employee e = genius.generateEmployee()
            log.info "Generated ${e.gender} Name:\t${e.firstName} ${e.lastName}, Company: ${e.companyName}, Job: ${e.jobName}"
            assert names.add("${e.firstName} ${e.lastName}")
            assert hashes.add(e.sha1NameHash)
            if (e.gender == Gender.Male) maleCount++ else femaleCount++
        }

        and: "we should have a relatively even gender distribution"
        assert Math.abs(femaleCount - maleCount) < TEST_RUNS / 100
    }

    def "should generate an avatar"() {
        expect: "a non-null avatar URL"
        Person p = genius.generate(true)
        assert p.avatarUrl
    }

    def "should generate an avatar for an employee"() {
        expect: "a non-null avatar URL"
        Employee e = genius.generateEmployee(true)
        assert e.avatarUrl
    }

    def "should generate an employee given a first / last name"(){
        expect: "An avatar URL, company name, and job name"
        Employee e = genius.generateEmployeeByName("First", "Last")
        assert e.firstName == "First"
        assert e.lastName == "Last"
        assert e.avatarUrl
        assert e.companyName
        assert e.jobName
    }
}
