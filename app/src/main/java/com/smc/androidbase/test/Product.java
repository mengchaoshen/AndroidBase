package com.smc.androidbase.test;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class Product {

    private String lock;

    public Product(String lock) {
        this.lock = lock;
    }

    public void setValue() {
        synchronized (lock) {
            if (!StringObject.value.equals("")) {
                try {
                    lock.wait();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String value = System.currentTimeMillis() + "";
            System.out.println("set的值是 " + value);
            StringObject.value = value;
            lock.notifyAll();
        }
    }
}
