package com.smc.androidbase.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class TransientTest {
    public final static String FILE_STR = "/Users/shenmengchao/Documents/arcvideo/log/test.txt";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User("Alexia", "123456");
        System.out.println("read before");
        System.out.println("name : " + user.getUserName() + ", pwd : " + user.getPwd());

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_STR));
        objectOutputStream.writeObject(user);
        objectOutputStream.flush();
        objectOutputStream.close();


        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_STR));
        User user1 = (User) objectInputStream.readObject();
        System.out.println("read after");
        System.out.println("name : " + user1.getUserName() + ", pwd : " + user1.getPwd());

    }


}
