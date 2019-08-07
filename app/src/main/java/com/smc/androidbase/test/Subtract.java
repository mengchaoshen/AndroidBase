package com.smc.androidbase.test;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Subtract {

    private String lock;

    public Subtract(String lock) {
        this.lock = lock;
    }

    public void subtract() {
        synchronized (lock) {
            while (ValueObject.list.size() == 0) {
                System.out.println(Thread.currentThread() + " -- wait begin");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + " -- wait end");
            }
            ValueObject.list.remove(0);
            System.out.println(Thread.currentThread() + " -- list.size = " + ValueObject.list.size());
        }
    }
}
