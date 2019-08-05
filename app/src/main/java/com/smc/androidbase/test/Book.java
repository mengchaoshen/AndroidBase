package com.smc.androidbase.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Book {

    Map<String, Integer> map = new HashMap<>();

    @MyAnnotation(name = "你的名字")
    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }
}
