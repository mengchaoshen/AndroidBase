package com.smc.androidbase.test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Ticket implements Runnable {

    private volatile int num = 100;
    //构造函数中的fair参数表示是否是公平锁
    //fair=true 各个线程就会依次获取到锁
    //ReentrantLock是一个可中断的锁
    private ReentrantLock lock = new ReentrantLock(true);
    private ReentrantLock lockA = new ReentrantLock(true);
    ReadWriteLock mReadWriteLock = new ReentrantReadWriteLock();

    public void a(){
        lock.lock();
        //..
        lockA.lock();
        // ..
        lockA.unlock();

        lock.unlock();
    }

    public void b(){
        //..
        lock.lock();

        lockA.lock();

        lockA.unlock();
        // ..
        lock.unlock();
    }


    @Override
    public void run() {

        while (true) {

            if (num > 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //这里加了synchronized 在 pr()内部也加了这里加了synchronized,由于锁是可重入的，所以
                //两个都可以获取到锁
                lock.lock();
                //中断锁，当当前线程等待时间过长，不想等待是，可以使用lockInterruptibly来中断
//                lock.lockInterruptibly();

//                mReadWriteLock.readLock().lock();
//                mReadWriteLock.writeLock().unlock();
                try {
                    pr();
                } finally {
                    //ReentrantLock由于不会自动释放，在出现异常的时候会导致死锁，所以需要在finally中去unlock
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 这里是static方法，所以不同对象之间也只有一个类锁
     */
    private synchronized void pr() {
        //这里加的是类锁，不同对象之间也只有一个类锁，如果加的是对象锁，还是会有同步问题的
//        lock.lock();
        if (num > 0) {
            System.out.println(Thread.currentThread().getName() + ", --sale--" + num--);
        }
//        lock.unlock();
    }
}

