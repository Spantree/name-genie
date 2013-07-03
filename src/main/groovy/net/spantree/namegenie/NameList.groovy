package net.spantree.namegenie

import groovy.transform.CompileStatic

@CompileStatic
class NameList {
    List<String> names = []

    NameList(String fileName) {
        URL url = getClass().getResource(fileName)
        if(!url) {
            throw new IllegalStateException("Could not find file ${fileName} in classpath")
        }
        File file = new File(url.path)
        addFile(file)
    }

    NameList(File file) {
        addFile(file)
    }

    private void addFile(File file) {
        file.eachLine { String line ->
            names << line.trim()
        }
    }

    String pickRandom() {
        int index = RandomSingleton.instance.nextInt(names.size())
        names[index]
    }
}
