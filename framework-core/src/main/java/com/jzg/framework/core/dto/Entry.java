package com.jzg.framework.core.dto;

import java.io.Serializable;

/**
 * 键值对
 */
public class Entry<K extends Serializable, V extends Serializable> implements Serializable {
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    private K key;
    private V value;
}
