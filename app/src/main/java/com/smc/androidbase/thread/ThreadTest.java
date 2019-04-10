package com.smc.androidbase.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ThreadTest {

    private List<Integer> mIntegerList = new ArrayList<>();

    public void put() {
        for (int i = 0; i < 1000; i++) {
            mIntegerList.add(i);
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (mIntegerList) {
                        if (mIntegerList.size() > 0) {
                            int i = mIntegerList.remove(0);
                            System.out.println("ThreadTest get=" + i);
                        } else {
                            System.out.println("ThreadTest get null");
                            break;
                        }
                    }
                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (mIntegerList) {
                    System.out.println("ThreadTest clear");
                    mIntegerList.clear();
                }
            }
        }).start();
    }
}
