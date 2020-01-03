package com.smc.androidbase.test;

/**
 * @author shenmengchao
 * @version 1.0.0
 */
public class TestPersion {

    public static void main(String[] args) throws CloneNotSupportedException {

//        Byte.BYTES;  //1字节
//        Short.BYTES; //2字节
        //Integer.BYTES; 4字节
//        Character.BYTES 2字节
        //System.out.println("Byte size " + size);
//
//        Person p1 = new Person("a", 1);
//        Person p2 = (Person) p1.clone();
//
//        String s1 = "abc";
//        String s2 = new String("abc");
//
//        System.out.println("p1.hashCode:" + p1.hashCode() + " " + p1 + " " + address(p1.hashCode()));
//        System.out.println("p2.hashCode:" + p2.hashCode());
//
//        System.out.println("s1.hashCode:" + s1.hashCode() + " " + s1);
//        System.out.println("s2.hashCode:" + s2.hashCode() + " " + s1);
//        System.out.println("s1 == s2 ? " + s2 == s1);
//
//        //Integer i = 100直接使用这种方法创建对象，会调用Integer.valueOf()方法，Integer内部有-128~127的缓存
//        //所以这个范围以内的对象创建，都是同一个对象
//        Integer i1 = 100;
//        Integer i2 = 100;
//        System.out.println("i1.hashCode:" + i1.hashCode());
//        System.out.println("i2.hashCode:" + i2.hashCode());
//        int i3 = 200;
//        System.out.println("i1==i2 ? " + (i1==i2));


        char c = 9;
        int i = (int)c;
        System.out.println(i);

    }

    private static String address(int hashCode) {
        return Integer.toHexString(hashCode);
    }


}
