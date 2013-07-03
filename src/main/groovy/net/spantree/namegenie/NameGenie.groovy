package net.spantree.namegenie

import groovy.transform.CompileStatic

import java.nio.ByteBuffer

@CompileStatic
class NameGenie {
    static final int RETRY_CAP = 1000

    NameList femaleFirstNames
    NameList maleFirstNames
    NameList lastNames
    Double femaleToMaleRatio
    Set<ByteBuffer> previousNameHashes = []

    NameGenie(double maleToFemaleRatio = 0.5) {
        this.femaleFirstNames = new NameList('firstNames_female.txt')
        this.maleFirstNames = new NameList('firstNames_male.txt')
        this.lastNames = new NameList('lastNames.txt')
        this.femaleToMaleRatio = maleToFemaleRatio
    }

    /**
     * Generates a person with a name unique from previously-generated names
     *
     * @return A person with a random combination of gender, first name and last name
     */
    Person generate() {
        Gender gender = (RandomSingleton.instance.nextDouble() < femaleToMaleRatio) ? Gender.Female : Gender.Male
        (gender == Gender.Female ? generateFemale() : generateMale())
    }

    Person generateFemale() {
        generateForGender(Gender.Female)
    }

    Person generateMale() {
        generateForGender(Gender.Male)
    }

    boolean nameAlreadyExists(Person person) {
        !previousNameHashes.add(person.sha1NameHash)
    }

    private Person generateForGender(Gender gender) {
        Person person = null
        NameList firstNameList = (gender == Gender.Female ? femaleFirstNames : maleFirstNames)
        int retries = 0
        while(retries++ < RETRY_CAP) {
            String firstName = firstNameList.pickRandom()
            String lastName = lastNames.pickRandom()
            person = new Person(firstName: firstName, lastName: lastName, gender: gender)
            if(!nameAlreadyExists(person)) {
                break
            }
        }
        if(retries >= RETRY_CAP) {
            throw new IllegalStateException("Tried to generate unique name ${retries} times, retry cap of ${RETRY_CAP} exceeded")
        }
        person
    }
}
