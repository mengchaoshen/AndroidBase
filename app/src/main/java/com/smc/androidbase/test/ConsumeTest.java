package com.smc.androidbase.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ConsumeTest {

    public static void main(String[] args) throws InterruptedException {
//        testRun();
//        testAdd();
//        testConsumer();
        testCondition();
    }

    /**
     * 使用ReentrantLock和Condition实现多生产者和多消费者模型
     */
    private static void testCondition() {

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        CProduct product = new CProduct(lock, condition);
        CConsumer consumer = new CConsumer(lock, condition);
        for (int i = 0; i < 3; i++) {
            Thread p = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true)
                        product.setValue();
                }
            });
            p.start();
            Thread c = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true)
                        consumer.getValue();
                }
            });
            c.start();
        }
    }

    private static void testConsumer() {
        String lock = "";
        final Product product = new Product(lock);
        final Consumer consumer = new Consumer(lock);
        Thread p = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                    product.setValue();
            }
        });
        p.start();
        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                    consumer.getValue();
            }
        });
        c.start();
    }

    private static void testAdd() throws InterruptedException {
        final String lock = new String("");
        Add add = new Add(lock);
        Subtract subtract = new Subtract(lock);

        Thread s1 = new Thread(new Runnable() {
            @Override
            public void run() {
                subtract.subtract();
            }
        });
        Thread s2 = new Thread(new Runnable() {
            @Override
            public void run() {
                subtract.subtract();
            }
        });
        s1.start();
        s2.start();
        Thread.sleep(1000);

        Thread a1 = new Thread(new Runnable() {
            @Override
            public void run() {
                add.add();
            }
        });
        a1.start();
    }

    private static void testRun() throws InterruptedException {
        MyRun myRun = new MyRun();
        Thread t1 = new Thread(myRun.runA);
        t1.start();

        Thread t3 = new Thread(myRun.runC);
        t3.start();

        Thread.sleep(1000);
        Thread t2 = new Thread(myRun.runB);
        t2.start();
    }
}
