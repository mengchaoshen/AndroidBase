package com.smc.androidbase.thread;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Yield {

    public void start(){
        YieldThread thread1 = new YieldThread();
        thread1.start();
        YieldThread thread2 = new YieldThread();
        thread2.start();
    }

    class YieldThread extends Thread{
        @Override
        public void run() {
            super.run();
            for(int i = 0; i < 100; i++){
                System.out.println(getName()+"当前i="+i);
                if(i == 2){
                    System.out.println(getName()+"礼让i="+i);
                    yield();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
