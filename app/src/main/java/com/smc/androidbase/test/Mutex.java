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
 */

public class Mutex implements Lock, Serializable {

    private final Sync sync = new Sync();


    public void lockShared() {
        //以共享的模式获取锁，也就是read lock
        sync.acquireShared(1);
    }

    @Override
    public void lock() {
        sync.acquire(1);
//        Thread.interrupted();
//        Thread thread = null;
//        thread.isInterrupted();
//        thread.interrupt();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, @NonNull TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @NonNull
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int acquires) {
            assert acquires == 1;
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int releases) {
            assert releases == 1;
            //getState()==0表示没有获取到锁，无法释放
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            //getState()==1表示当前线程获取到锁
            return getState() == 1;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }
}
