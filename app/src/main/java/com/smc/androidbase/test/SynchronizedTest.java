package com.smc.androidbase.test;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class SynchronizedTest {

    public static void main(String[] args) {

        //这里是不同的对象，所以加对象锁是无效的
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        Ticket ticket4 = new Ticket();

        Thread t1 = new Thread(ticket1);
        Thread t2 = new Thread(ticket1);
        Thread t3 = new Thread(ticket1);
        Thread t4 = new Thread(ticket1);

        t1.start();
        t2.start();
//        t3.start();
//        t4.start();
    }
}
