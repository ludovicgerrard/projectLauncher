package com.zkteco.util;

public final class Pair<K, V> {
    private final K entry;
    private final V value;

    public Pair(K k, V v) {
        this.entry = k;
        this.value = v;
    }

    public K getEntry() {
        return this.entry;
    }

    public V getValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        if (!getEntry().equals(pair.getEntry())) {
            return false;
        }
        return getValue().equals(pair.getValue());
    }

    public int hashCode() {
        return (getEntry().hashCode() * 31) + getValue().hashCode();
    }

    public String toString() {
        return "Pair{entry=" + this.entry + ", value=" + this.value + '}';
    }
}
