package com.smc.androidbase.test;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Consumer {

    private String lock;

    public Consumer(String lock) {
        this.lock = lock;
    }

    public void getValue() {
        synchronized (lock) {
            //没有值，进行wait
            if (StringObject.value.equals("")) {
                try {
                    lock.wait();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("得到的value " + StringObject.value);
            StringObject.value = "";
            //消费完成之后唤醒其他线程
            lock.notifyAll();
        }
    }
}
