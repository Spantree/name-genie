package net.spantree.namegenie

import groovy.transform.CompileStatic

import java.nio.ByteBuffer
import java.security.MessageDigest

@CompileStatic
class NameGenie {
    static final int RETRY_CAP = 1000

    NameList femaleFirstNames
    NameList maleFirstNames
    NameList lastNames
    NameList jobNames
    NameList companyNames
    Map<String, NameList> nameToAvatarMap
    Double femaleToMaleRatio
    Set<ByteBuffer> previousNameHashes = []

    NameGenie(double maleToFemaleRatio = 0.5, String nameToAvatarMap = "avatarMappings.txt") {
        this.femaleFirstNames = new NameList('firstNames_female.txt')
        this.maleFirstNames = new NameList('firstNames_male.txt')
        this.lastNames = new NameList('lastNames.txt')
        this.jobNames = new NameList('jobNames.txt')
        this.companyNames = new NameList('companyNames.txt')
        this.nameToAvatarMap = generateNameToAvatarMap(getClass().getResourceAsStream(nameToAvatarMap))
        this.femaleToMaleRatio = maleToFemaleRatio
    }

    /**
     * Generates a person with a name unique from previously-generated names
     *
     * @return A person with a random combination of gender, first name and last name
     */
    Person generate(avatar = false) {
        Gender gender = (RandomSingleton.instance.nextDouble() < femaleToMaleRatio) ? Gender.Female : Gender.Male
        (gender == Gender.Female ? generateFemale(avatar) : generateMale(avatar))
    }

    Person generateFemale(avatar = false) {
        generateForGender(Gender.Female, avatar)
    }

    Person generateMale(avatar = false) {
        generateForGender(Gender.Male, avatar)
    }

    Employee generateEmployee(avatar = false) {
        def person = generate(avatar)
        generateEmployeeForPerson(person)
    }

    Employee generateEmployeeForPerson(Person person) {
        def jobName = jobNames.pickRandom()
        def companyName = companyNames.pickRandom()

        new Employee(firstName: person.firstName, lastName: person.lastName, gender: person.gender, avatarUrl: person.avatarUrl, jobName: jobName, companyName: companyName)
    }

    boolean nameAlreadyExists(Person person) {
        !previousNameHashes.add(person.sha1NameHash)
    }

    Employee generateEmployeeByName(String firstName, String lastName = "Last") {
        def jobName = jobNames.pickRandom()
        def companyName = companyNames.pickRandom()

        def avatarUrl
        if (nameToAvatarMap.containsKey(firstName)) {
            avatarUrl = nameToAvatarMap.get(firstName).pickRandom()
        } else {
            MessageDigest digest = MessageDigest.getInstance("MD5")
            def sampleEmail = firstName.toLowerCase().trim() + "@gravatar.com"
            digest.update(sampleEmail.bytes)

            BigInteger big = new BigInteger(1, digest.digest())
            String md5 = big.toString(16).padLeft(32, "0")

            avatarUrl = "http://gravatar.com/avatar/" + md5 + "?d=identicon"
        }
        new Employee(firstName: firstName, lastName: lastName, avatarUrl: avatarUrl, jobName: jobName, companyName: companyName)

    }

    private Person generateForGender(Gender gender, avatar = false) {
        Person person = null
        NameList firstNameList = (gender == Gender.Female ? femaleFirstNames : maleFirstNames)
        int retries = 0
        while (retries++ < RETRY_CAP) {
            String firstName = firstNameList.pickRandom()
            String lastName = lastNames.pickRandom()
            person = new Person(firstName: firstName, lastName: lastName, gender: gender)
            if (!nameAlreadyExists(person)) {
                if (avatar) {
                    if (nameToAvatarMap.containsKey(firstName)) {
                        person.avatarUrl = nameToAvatarMap.get(firstName).pickRandom()
                        break
                    }
                } else {
                    break
                }
            }

        }
        if (retries >= RETRY_CAP) {
            throw new IllegalStateException("Tried to generate unique name ${retries} times, retry cap of ${RETRY_CAP} exceeded")
        }
        person
    }

    private Map<String, NameList> generateNameToAvatarMap(InputStream stream) {
        def map = new HashMap<String, NameList>()
        stream.eachLine {
            String line ->
                def firstName = line.split()[0]
                def avatarUrl = line.split()[1]
                if (!map.containsKey(firstName)) {
                    map.put(firstName, new NameList())
                }
                map.get(firstName).add(avatarUrl)
        }
        map
    }

    void setNameToAvatarMap(InputStream stream) {
        this.nameToAvatarMap = generateNameToAvatarMap(stream)
    }

    void setFemaleFirstNames(InputStream stream) {
        this.femaleFirstNames = new NameList(stream)
    }

    void setMaleFirstNames(InputStream stream) {
        this.maleFirstNames = new NameList(stream)
    }

    void setLastNames(InputStream stream) {
        this.lastNames = new NameList(stream)
    }

    void setJobNames(InputStream stream) {
        this.jobNames = new NameList(stream)
    }

    void setCompanyNames(InputStream stream) {
        this.companyNames = new NameList(stream)
    }

    void setFemaleToMaleRatio(Double femaleToMaleRatio) {
        this.femaleToMaleRatio = femaleToMaleRatio
    }
}
