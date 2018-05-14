package com.jzg.framework.core.vo;

import com.jzg.framework.core.dto.Entry;

import java.io.Serializable;

/**
 * 键值对
 */
public class EntryVo<K extends Serializable, V extends Serializable> {
    public EntryVo(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public EntryVo(Entry<K, V> entryDto){
        this.key = entryDto.getKey();
        this.value = entryDto.getValue();
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






    /**
     * 转化为EntryDto
     * @return
     */
    public Entry toEntryDto(){
        return new Entry<K, V>(this.getKey(), this.getValue());
    }
}
