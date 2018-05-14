package com.jzg.framework.core.event;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Event<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 4599689652110257386L;

    private Map<String, String> headers = new HashMap();
    private T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }




    public String getHeader(String name) {
        if ((name == null) || (name.equals(""))) {
            throw new IllegalArgumentException("event header name cannot is null");
        }
        return (String) this.headers.get(name.toLowerCase());
    }

    public void setHeader(String name, String value) {
        if ((name == null) || (name.equals(""))) {
            throw new IllegalArgumentException("event header name cannot is null");
        }
        this.headers.put(name.toLowerCase(), value);
    }

    public String removeHeader(String name) {
        if ((name == null) || (name.equals(""))) {
            throw new IllegalArgumentException("event header name cannot is null");
        }
        return (String) this.headers.remove(name.toLowerCase());
    }

    public boolean hasHeaders() {
        return (this.headers != null) && (!this.headers.isEmpty());
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }
}
