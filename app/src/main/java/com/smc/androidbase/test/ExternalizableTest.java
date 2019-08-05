package com.smc.androidbase.test;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ExternalizableTest implements Externalizable {

    public final static String FILE_STR = "/Users/shenmengchao/Documents/arcvideo/log/test.txt";

    //使用实现Externalizable接口，并在writeExternal()和readExternalizable()中把熟悉写入和读出的话，
    //不管属性有没有添加transient关键字，都是可以被序列化的
    private transient String content = "我是content";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(content);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        content = (String) in.readObject();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ExternalizableTest externalizableTest = new ExternalizableTest();
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(FILE_STR));
        out.writeObject(externalizableTest);

        ObjectInput in = new ObjectInputStream(new FileInputStream(FILE_STR));
        ExternalizableTest externalizableTest1 = (ExternalizableTest) in.readObject();
        System.out.println("externalizableTest1:" + externalizableTest1.toString());
    }
}
