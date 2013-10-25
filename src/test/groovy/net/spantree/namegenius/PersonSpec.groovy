package net.spantree.namegenius

import spock.lang.Specification

class PersonSpec extends Specification {
    def "should generate sha1 hashes unique to name only"() {
        setup:
        def cedric = new Person(firstName: 'Cedric', lastName: 'Hurst', gender: Gender.Male)
        def kellyMale = new Person(firstName: 'Kelly', lastName: 'Hurst', gender: Gender.Male)
        def kellyFemale = new Person(firstName: 'Kelly', lastName: 'Hurst', gender: Gender.Female)
        def ofApostrophe = new Person(firstName: 'Kelly', lastName: 'O\'Flannagan', gender: Gender.Female)
        def ofNoApostrophe = new Person(firstName: 'Kelly', lastName: 'OFlannagan', gender: Gender.Female)
        def ofLowercase = new Person(firstName: 'kelly', lastName: 'oflannagan', gender: Gender.Female)

        expect:
        cedric.sha1NameHash != kellyMale.sha1NameHash
        kellyMale.sha1NameHash == kellyFemale.sha1NameHash
        ofApostrophe.sha1NameHash == ofNoApostrophe.sha1NameHash
        ofApostrophe.sha1NameHash == ofLowercase.sha1NameHash
    }
}
