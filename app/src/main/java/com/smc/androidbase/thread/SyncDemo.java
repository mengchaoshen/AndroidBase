package com.smc.androidbase.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class SyncDemo {

    private int ticket = 10;
    Lock lock = new ReentrantLock();

    public synchronized void syncMethod() {
        for (int i = 0; i < 1000; i++) {
            ticket--;
            System.out.println("threadName=" + Thread.currentThread().getName() + ", 车票剩余：" + ticket);
        }
    }

    public void syncThis() {
        synchronized (this) {
            for (int i = 0; i < 1000; i++) {
                ticket--;
                System.out.println("threadName=" + Thread.currentThread().getName() + ", 车票剩余：" + ticket);
            }
        }
    }

    public void syncClass() {
        synchronized (SyncDemo.class) {
            for (int i = 0; i < 1000; i++) {
                ticket--;
                System.out.println("threadName=" + Thread.currentThread().getName() + ", 车票剩余：" + ticket);
            }
        }
    }

    //在使用ReentrantLock时需要注意的是，lock一定是要在全局创建，否则每次调用syncReenLock()方法时，都创建一个lock的话，还是线程不安全的
    public void syncReenLock() {
        lock.lock();
        for (int i = 0; i < 1000; i++) {
            ticket--;
            System.out.println("threadName=" + Thread.currentThread().getName() + ", 车票剩余：" + ticket);
        }
        lock.unlock();
    }
}
