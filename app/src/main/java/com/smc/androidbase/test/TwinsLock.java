package com.smc.androidbase.test;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author shenmengchao
 * @version 1.0.0
 *          实现一个锁可以最多2个线程访问（shared lock）
 */
public class TwinsLock implements Lock, Serializable {

    public static void main(String[] args) {
        final TwinsLock twinsLock = new TwinsLock();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                twinsLock.lock();
                System.out.println("thread1 print");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1 end");
                twinsLock.unlock();
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                twinsLock.lock();
                System.out.println("thread2 print");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 end");
                twinsLock.unlock();
            }
        });
        thread2.start();
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                twinsLock.lock();
                System.out.println("thread3 print");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread3 end");
                twinsLock.unlock();
            }
        });
        thread3.start();
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                twinsLock.lock();
                System.out.println("thread4 print");
                twinsLock.unlock();
            }
        });
        thread4.start();
    }

    Sync mSycn = new Sync(2);

    @Override
    public void lock() {
        mSycn.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        mSycn.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return mSycn.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, @NonNull TimeUnit unit) throws InterruptedException {
        return mSycn.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        mSycn.releaseShared(1);
    }

    @NonNull
    @Override
    public Condition newCondition() {
        return null;
    }

    private static class Sync extends AbstractQueuedSynchronizer {

        Sync(int count) {
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int acquires) {
            for (; ; ) {
                int current = getState();
                int newState = current - acquires;
//                System.out.println(Thread.currentThread() + "--tryAcquireShared current=" + current + ", newState=" + newState);
                if (newState < 0 || compareAndSetState(current, newState)) {
//                    System.out.println(Thread.currentThread() + "--tryAcquireShared success newState=" + newState);
                    return newState;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int release) {
            for (; ; ) {
                int current = getState();
                int newState = current + release;
//                System.out.println(Thread.currentThread() + "--tryReleaseShared current=" + current + ", newState=" + newState);
                if (compareAndSetState(current, newState)) {
//                    System.out.println(Thread.currentThread() + "--tryReleaseShared success newState=" + newState);
                    return true;
                }
            }
        }
    }
}
