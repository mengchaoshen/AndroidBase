package com.smc.androidbase.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author shenmengchao
 * @version 1.0.0
 *          使用输入输出流序列化的形式实现深拷贝
 */

public class CopyTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ColoredCircle coloredCircle = new ColoredCircle(100, 100);
        System.out.println("1:" + coloredCircle.toString());
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(coloredCircle);
        ByteArrayInputStream bai = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bai);
        ColoredCircle copy = (ColoredCircle) ois.readObject();
        System.out.println("2:" + copy.toString());

        coloredCircle.setX(200);
        coloredCircle.setY(200);
        System.out.println("1:" + coloredCircle.toString());
        System.out.println("2:" + copy.toString());
    }

}
