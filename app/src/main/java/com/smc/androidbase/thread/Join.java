package com.smc.androidbase.thread;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Join {

    /**
     * 这里在线程启动的时候添加执行了join()方法，这样就会等待这个线程执行完毕之后，才会去执行主线程
     */
    public void start() {

        System.out.println("主线程开始执行");
        JoinThread thread1 = new JoinThread();
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程在执行1");
        JoinThread thread2 = new JoinThread();
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程在执行2");
    }

    class JoinThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                System.out.println(getName() + "开始执行");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + "执行完毕");
        }
    }
}
