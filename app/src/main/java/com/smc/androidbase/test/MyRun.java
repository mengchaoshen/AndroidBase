package com.smc.androidbase.test;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class MyRun {

    private String lock = new String("");

    private String lockC = new String("");

    public Runnable runA = new Runnable() {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("begin wait");
                try {
                    //wait都需要在获取锁的情况下才能执行，wait会让当前线程会进入挂起状态，并且释放锁
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end wait");
            }
        }
    };

    public Runnable runB = new Runnable() {
        @Override
        public void run() {
            synchronized (lock) {
                synchronized (lockC) {
                    System.out.println("begin notify");
                    //notify会唤醒挂起状态的线程 只会唤醒使用lock对象挂起的线程
                    //notify执行一次会唤醒一个被wait的线程，如果有两个线程wait就需要执行两次notify
                    //notifyAll会唤醒所有使用当前对象wait挂起的线程
                    lock.notify();
                    lock.notify();
                    System.out.println("end notify");
                }
            }
        }
    };

    public Runnable runC = new Runnable() {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("begin wait lockC");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end wait lockC");
            }
        }
    };
}
