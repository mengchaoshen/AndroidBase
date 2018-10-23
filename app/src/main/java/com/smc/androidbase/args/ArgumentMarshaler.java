package com.smc.androidbase.args;

import java.util.Iterator;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/10/16
 * @description
 */

public interface ArgumentMarshaler {

    void set(Iterator<String> currentArgument) throws ArgsException;
}
