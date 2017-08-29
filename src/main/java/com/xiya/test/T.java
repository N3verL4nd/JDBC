package com.xiya.test;

import com.xiya.entity.CaseInsensitiveHashMap;

public class T {
    public static void main(String[] args) {
        CaseInsensitiveHashMap map = new CaseInsensitiveHashMap();
        map.put("QQQ", "111");
        map.put("WWW", "222");
        map.put("eee", "333");
        System.out.println(map);
    }
}
