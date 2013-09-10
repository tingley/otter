package com.spartansoftwareinc.otter;

class Hasher {
    private int hash = 17;
    
    Hasher() {
    }
    
    Hasher(int seed) {
        hash = seed;
    }
    
    int value() {
        return hash;
    }
    
    Hasher hash(int i) {
        hash = hash * 31 + i;
        return this;
    }
    
    Hasher add(Object o) {
        if (o != null) {
            hash = hash * 31 + o.hashCode();
        }
        return this;
    }
}
