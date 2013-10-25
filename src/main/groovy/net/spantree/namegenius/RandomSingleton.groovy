package net.spantree.namegenius

import groovy.transform.CompileStatic

@CompileStatic
@Singleton
class RandomSingleton {
    private Random random = new Random()

    double nextDouble() {
        random.nextDouble()
    }

    int nextInt(int n) {
        random.nextInt(n)
    }
}
