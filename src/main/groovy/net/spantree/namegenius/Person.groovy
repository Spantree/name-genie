package net.spantree.namegenius

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

import java.nio.ByteBuffer
import java.security.MessageDigest

@CompileStatic
@EqualsAndHashCode(includes = 'firstName, lastName, gender')
class Person {
    String firstName
    String lastName
    Gender gender
    String avatarUrl

    ByteBuffer sha1NameHash

    private String getStemmedName() {
        "$firstName$lastName".toLowerCase().replaceAll(/\W/, '')
    }

    ByteBuffer getSha1NameHash() {
        if(sha1NameHash == null) {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(stemmedName.getBytes('UTF-8'))
            sha1NameHash = ByteBuffer.wrap(md.digest())
        }
        this.@sha1NameHash
    }

    String toString() {
        "Person(gender=${gender.name()}, firstName=${firstName}, lastName=${lastName})"
    }
}