package com.smc.androidbase.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class CConsumer {

    private ReentrantLock mReentrantLock;
    private Condition mCondition;

    public CConsumer(ReentrantLock reentrantLock, Condition condition) {
        mReentrantLock = reentrantLock;
        mCondition = condition;
    }

    public void getValue() {
        try {
            mReentrantLock.lock();
            //没有值，进行wait
            while (StringObject.value.equals("")) {
                try {
                    mCondition.await();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread() + " --- 得到的value " + StringObject.value);
            StringObject.value = "";
            //消费完成之后唤醒其他线程
            mCondition.signalAll();
        } finally {
            mReentrantLock.unlock();
        }

    }

}
