package com.smc.androidbase;

import android.util.Log;

import com.smc.androidbase.thread.ThreadTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/9/21
 * @description
 */

public class Test {

    private static int mIndex;

    private static int mIndex1;
    private static int mIndex2;
    private static int mIndex3;
    private static int mIndex4;

    private static List<Integer> mList1 = new ArrayList<>();
    private static List<Integer> mList3 = new ArrayList<>();

    public static <T> T get() {
        Integer i = 123;
        return (T) i;
    }

    public static class Apple {
    }

    public static class EnglishBook extends Book{

    }

    public static class Book<E> {
        //泛型构造器
        public <T> Book(T t) {
            System.out.println(t);
        }

        public Book() {
        }

    }


    public static void main(String[] args) {
        EnglishBook englishBook = new EnglishBook();

        List<? extends Book> list = new ArrayList<>();

        Book b = new Book(1);
        Book<Integer> b1 = new <String>Book("s");

        int a = get();
        System.out.println(a);

//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(3);
//        list.add(2);
//
//        Collections.sort(list, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                //return > 0 o1和o2替换位置
//                return o1 - o2;
//            }
//        });
//        System.out.print(list);

//        System.out.println(testRegular("01"));
//        System.out.println(testRegular("03"));
//        System.out.println(testRegular("000"));
//        System.out.println(testRegular("123"));
//
//        int width = 1281;
//        int height = 720;
//        float ratio = (float)width / (float)height;
//        width = Float.floatToIntBits(Float.intBitsToFloat(height)*ratio);
//
//        System.out.println(width);
//        ThreadTest threadTest = new ThreadTest();
//        threadTest.put();
//        threadTest.start();

//        test();
    }

    private static boolean testRegular(String str) {
        return str.matches("((0[0-9])|(000))");
    }

    private static void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int value1 = (int) (Math.random() * 1000);
                    int value2 = (int) (Math.random() * 1000);
                    int value3 = (int) (Math.random() * 1000);
                    int value4 = (int) (Math.random() * 1000);
                    mIndex++;
                    mIndex1++;
                    mIndex2++;
                    mIndex3++;
                    mIndex4++;
//                    if (value1 < 7 ) {
//                        System.out.println("恭喜沈梦超在第" + mIndex1 + "次中签");
//                        mIndex1 = 1;
//                    }
//                    if (value2 < 7 ) {
//                        System.out.println("恭喜郑林吉在第" + mIndex2 + "次中签");
//                        mIndex2 = 1;
//                    }
//                    if (value3 < 7 ) {
//                        System.out.println("恭喜张楠在第" + mIndex3 + "次中签");
//                        mIndex3 = 1;
//                    }
//                    if (value4 < 7 ) {
//                        System.out.println("恭喜王萍在第" + mIndex4 + "次中签");
//                        mIndex4 = 1;
//                    }

                    if (value1 < 7 || value2 < 7) {
                        mList1.add(mIndex1);
                        System.out.println("恭喜沈梦超|郑林吉在第" + mIndex1 + "次中签,平均次数=" + average(mList1));
                        mIndex1 = 1;
                        mIndex2 = 1;
                    }
                    if (value3 < 7 || value4 < 7) {
                        mList3.add(mIndex3);
                        System.out.println("恭喜张楠|王萍在第" + mIndex3 + "次中签, 平均次数=" + average(mList3));
                        mIndex3 = 1;
                        mIndex4 = 1;
                    }


//                    if (value1 < 7 || value2 < 7 || value3 < 7 || value4 < 7) {
//                        System.out.println("恭喜在第" + mIndex + "次中签");
//                        mIndex = 1;
//                    }else{
////                        System.out.println("不好意思，第" + mIndex + "次未中签");
//                    }
                }
            }
        }).start();
    }

    private static int average(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum / list.size();
    }

}
