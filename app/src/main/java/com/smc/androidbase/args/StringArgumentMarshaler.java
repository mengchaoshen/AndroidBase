package com.smc.androidbase.args;

import java.util.Iterator;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/10/16
 * @description
 */

public class StringArgumentMarshaler implements ArgumentMarshaler {

    private String stringValue = "";

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        stringValue = currentArgument.next();
    }

    public static String getValue(ArgumentMarshaler am) {
        if (am != null && am instanceof StringArgumentMarshaler) {
            return ((StringArgumentMarshaler) am).stringValue;
        }
        return "";
    }
}
