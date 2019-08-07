package com.smc.androidbase.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class CallableThreadTest implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        int i = 0;
        //callable线程进行循环
        for (i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "的循环变量i=" + i);
        }
        return i;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableThreadTest callableThreadTest = new CallableThreadTest();
        FutureTask<Integer> task = new FutureTask<>(callableThreadTest);
        //主线程进行循环
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "-的循环变量i=" + i);
            if (i == 20) {
                new Thread(task, "HasReturn-").start();
            }
        }
        System.out.println("有返回值的线程 return=" + task.get());
    }
}
