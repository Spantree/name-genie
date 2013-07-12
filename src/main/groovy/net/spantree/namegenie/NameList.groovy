package net.spantree.namegenie

import groovy.transform.CompileStatic

@CompileStatic
class NameList {
    List<String> names = []

    NameList() {}

    NameList(String fileName) {
        def stream = getClass().getResourceAsStream(fileName)
        addStream(stream)
    }

    NameList(File file) {
        addStream(file.newInputStream())
    }

    NameList(InputStream stream){
        addStream(stream)
    }

    private void addStream(InputStream file) {
        file.eachLine { String line ->
            line = line.trim()
            if (line) names << line
        }
    }

    void add(String name) {
        names.add(name)
    }

    String pickRandom() {
        int index = RandomSingleton.instance.nextInt(names.size())
        names[index]
    }
}
