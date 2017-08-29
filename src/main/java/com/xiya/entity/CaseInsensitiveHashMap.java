package com.xiya.entity;

import java.util.HashMap;
import java.util.Map;

public class CaseInsensitiveHashMap extends HashMap<String, Object> {
    private Map<String, String> map = new HashMap<>();
    public Object get(String key) {

        return super.get(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public Object put(String key, Object value) {
        String lower = key.toLowerCase();
        map.put(key, lower);

        return super.put(lower, value);
    }

    public Object remove(String key) {
        map.remove(key);
        return super.remove(key.toLowerCase());
    }
}
