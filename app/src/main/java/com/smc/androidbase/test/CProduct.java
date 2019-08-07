package com.smc.androidbase.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class CProduct {


    private ReentrantLock mReentrantLock;
    private Condition mCondition;

    public CProduct(ReentrantLock reentrantLock, Condition condition) {
        mReentrantLock = reentrantLock;
        mCondition = condition;
    }

    public void setValue() {
        try {
            mReentrantLock.lock();
            while (!StringObject.value.equals("")) {
                try {
                    mCondition.await();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String value = System.currentTimeMillis() + "";
            System.out.println(Thread.currentThread() + " --- set的值是 " + value);
            StringObject.value = value;
            mCondition.signalAll();
        } finally {
            mReentrantLock.unlock();
        }

    }
}
