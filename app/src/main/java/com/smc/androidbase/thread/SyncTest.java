package com.smc.androidbase.thread;

/**
 * @author shenmengchao
 * @version 1.0.0
 * https://blog.csdn.net/qq_30379689/article/details/53863082
 * 这个博客写的不错
 */

public class SyncTest {


    public static void main(String[] args) {
//        Ticket ticket = new Ticket();
//        ticket.startSell();

        //同一个对象，使用对象锁
        //由于是同一个对象，在不同线程中，执行方法，会按照线程创建顺序执行
//        final SyncDemo syncDemo = new SyncDemo();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo.syncMethod();
//            }
//        }.start();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo.syncThis();
//            }
//        }.start();
        //不同的对象，使用对象锁
        //由于是不同的对象，所以对象锁互不影响，两个线程执行交替进行
//        final SyncDemo syncDemo1 = new SyncDemo();
//        final SyncDemo syncDemo2 = new SyncDemo();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo1.syncMethod();
//            }
//        }.start();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo2.syncMethod();
//            }
//        }.start();
        //同一个对象，使用对象锁和类锁
        //由于对象锁和类锁，互补影响，所以两个线程也是交替进行，出现的结果有时候也会不正常，线程不全
//        final SyncDemo syncDemo3 = new SyncDemo();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo3.syncMethod();
//            }
//        }.start();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo3.syncClass();
//            }
//        }.start();

        //同一个对象，两个线程都使用类锁
        //由于使用的都是类锁，所以会等先创建的线程执行完后，再执行后面的
//        final SyncDemo syncDemo4 = new SyncDemo();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo4.syncClass();
//            }
//        }.start();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo4.syncClass();
//            }
//        }.start();

        //两个对象，都是类锁
        //由于两个对象都是同一个类，所以线程安全，需要等第一个线程执行完后，才会执行第二个线程
//        final SyncDemo syncDemo5 = new SyncDemo();
//        final SyncDemo syncDemo6 = new SyncDemo();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo5.syncClass();
//            }
//        }.start();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo6.syncClass();
//            }
//        }.start();

        //一个对象，使用 ReenLock
        //效果和锁对象一样，会按照线程的创建先后顺序执行
//        final SyncDemo syncDemo7 = new SyncDemo();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo7.syncReenLock();
//            }
//        }.start();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                syncDemo7.syncReenLock();
//            }
//        }.start();

        WaitAndNotify waitAndNotify = new WaitAndNotify();
        waitAndNotify.start();
//        waitAndNotify.startThreadControl();

//        Join join = new Join();
//        join.start();
//        Yield yield = new Yield();
//        yield.start();
    }


    public static class Ticket {

        private int ticket = 10;

        public void startSell() {
            for (int i = 0; i < 10; i++) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        sellTicket();
                    }
                }.start();
            }
        }

        //1.不加同步，在多线程中，会出现资源抢占的情况，剩余票数不是按照顺序下降的
        //2.在这里添加了synchronized关键字后，不同线程在执行这个方法时，都需要等其他线程执行完后才能执行，所以不会乱
        //3.在方法里面添加synchronized(this){}代码块，跟在方法上添加synchronized关键字效果一样
        private void sellTicket() {
            synchronized (this) {
                ticket--;
                System.out.println("车票剩余：" + ticket);
            }
        }

    }
}
