package com.smc.androidbase.serializable;

import java.io.Serializable;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/8/27
 * @description
 */

public class Serialization implements Serializable {

    public final static long Serializable = 1L;

    //序列化
    // 实现Serializable接口
    //1.实现方式比较简单，只要实现Serializable接口即可
    //2.Serializable是通过IO读写到硬盘中的，所以可以用于持久化保存成文件，但也是因为这个，所以效率会比较低
    //3.Serializable会定义一个long字段Serializable，用于作为类的标识（比如 A.class序列化成字符序列后，
    // A.class添加或删除了某些字段，只要字段Serializable的值没有改变，就还是可以反序列化，如果改变了，就无法反序列化）



}
