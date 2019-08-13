package com.smc.androidbase.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shenmengchao
 * @version 1.0.0
 *          ArrayBlockingQueue的实现类似
 *          取它最关键加入和取出队列的部分
 */
public class ConsumerQueue<T> {

    private final T[] items;
    int count;
    int head;
    int tail;
    ReentrantLock mReentrantLock = new ReentrantLock();
    Condition notFull = mReentrantLock.newCondition();
    Condition notEmpty = mReentrantLock.newCondition();

    public static void main(String[] args) {
        ConsumerQueue<Integer> consumerQueue = new ConsumerQueue<>();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    consumerQueue.put(i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    consumerQueue.get();
                }
            }
        });
        t2.start();
    }

    ConsumerQueue() {
        items = (T[]) new Object[10];
    }

    public void put(T t) {
        try {
            mReentrantLock.lock();
            while (items.length == count) {
                //执行await()操作的时候，会自动释放锁
                notFull.await();
            }
            System.out.println("put value = " + t + ", tail = " + tail);
            items[tail] = t;
            if (++tail == items.length) {
                tail = 0;
            }
            count++;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mReentrantLock.unlock();
        }
    }

    public void get() {
        try {
            mReentrantLock.lock();
            while (count == 0) {
                notEmpty.await();
            }
            T t = items[head];
            System.out.println("get value = " + t + ", head = " + head);
            items[head] = null;
            if (++head == items.length) {
                head = 0;
            }
            count--;
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mReentrantLock.unlock();
        }
    }
}
