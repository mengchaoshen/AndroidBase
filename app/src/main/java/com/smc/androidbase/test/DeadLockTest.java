package com.smc.androidbase.test;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class DeadLockTest {


    public static void main(String[] args) {
//        testStaticDeadLock();
        testDynamicDeadLock();
    }

    private static void testDynamicDeadLock() {
        final DynamicDeadLock dynamicDeadLock = new DynamicDeadLock();
        final String from = "f";
        final String to = "t";
        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamicDeadLock.test(from, to);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamicDeadLock.test(to, from);
            }
        }).start();
    }

    private static void testStaticDeadLock() {
        final StaticDeadLock staticDeadLock = new StaticDeadLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    staticDeadLock.a();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    staticDeadLock.b();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static class StaticDeadLock {
        private Object lockA = new Object();
        private Object lockB = new Object();

        //这样就会发生静态的锁顺序死锁
        //a()获取到了lockA后等待获取lockB，但是这个时候b()已经获取到lockB等待lockA。
        //两个方法之间就发生了死锁
        //解决方法就是，在都需要加锁的地方，加锁的顺序必须要相同
        public void a() throws InterruptedException {
            synchronized (lockA) {
                Thread.sleep(10);
                synchronized (lockB) {
                    System.out.println("a()");
                }
            }
        }

        public void b() throws InterruptedException {
            synchronized (lockB) {
                Thread.sleep(10);
                synchronized (lockA) {
                    System.out.println("b()");
                }
            }
        }
    }

    static class DynamicDeadLock {
        private Object lockA = new Object();
        private Object lockB = new Object();

        public void test(String from, String to) {
            //拿到from锁的在等待to锁 to锁的在等待from锁
            //修改为，使用System.identityHashCode()来获取对象的hash值，根据比较hash值的大小，来确定加锁顺序
            //这样就可以保证加锁的顺序是一致的
            synchronized (from) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (to) {
                    System.out.println("test()");
                }
            }
        }
    }
}
