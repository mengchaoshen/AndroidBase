package com.smc.androidbase.thread;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class WaitAndNotify {

    private Object mObject = new Object();

    public void start() {
        NotifyThread notifyThread = new NotifyThread(mObject);
        notifyThread.start();

        long start = System.currentTimeMillis();
        synchronized (mObject){
            try {
                //调用wait方法，如果在方法里面写入millis时长，当时间过去后，会自动进入run状态
                //如果在时间之前，有其他地方执行notify,将会提前进入run状态
                mObject.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("时间间隔=" + (end - start));

    }

    public class NotifyThread extends Thread {

        private Object mObject;

        public NotifyThread(Object o) {
            mObject = o;
        }

        @Override
        public void run() {
            super.run();
            synchronized (mObject) {
                try {
                    Thread.sleep(500);
                    mObject.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 使用wait()和notify()的配合使用，可以达到两个线程交替行进的效果
     */
    public void startThreadControl(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                for(int i = 0; i < 50; i++){
                    for(int j = 0; j < 10; j++){
                        System.out.println("子线程第"+i+"-"+j);
                    }
                    synchronized (mObject){
                        try {
                            mObject.notify();
                            mObject.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

        for(int i = 0; i < 50; i++){
            synchronized (mObject){
                try {
                    mObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int j = 0; j < 10; j++){
                System.out.println("主线程第"+i+"-"+j);
            }
            synchronized (mObject){
                mObject.notify();
            }

        }
    }
}
