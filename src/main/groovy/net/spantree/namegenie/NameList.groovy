package net.spantree.namegenie

import groovy.transform.CompileStatic

@CompileStatic
class NameList {
    List<String> names = []

    NameList(String fileName) {
        def stream = getClass().getResourceAsStream(fileName)
        addStream(stream)
    }

    NameList(File file) {
        addStream(file.newInputStream())
    }

    private void addStream(InputStream file) {
        file.eachLine { String line ->
            names << line.trim()
        }
    }

    String pickRandom() {
        int index = RandomSingleton.instance.nextInt(names.size())
        names[index]
    }
}
