package com.smc.androidbase.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ThreadPoorTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        threadPoorTest();
        testNewScheduledThreadPool();
    }

    private static void testNewFixedThreadPool() {
        int newFixedCoreSize = 4;
        //newFixedThreadPool 设置n个最大核心线程数，并且只能有n个线程，也就是说，这个线程池，永远存活n个线程
        //只有当线程池被销毁时，线程才会被销毁
        //好处是响应会比较及时
        ExecutorService executorService = Executors.newFixedThreadPool(newFixedCoreSize);
        executorService = new ThreadPoolExecutor(newFixedCoreSize, newFixedCoreSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    private static void testNewCachedThreadPool() {
        //newCachedThreadPool的核心线程数为0,但是线程总数为无穷大，超时时间为60s
        //所用队列为SynchronousQueue也就是说，一旦有任务加入，并且没有可用的线程，就会立即新建线程
        //当没有任务加入，超时过后，有可能会没有可用的线程。
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

    private static void testNewScheduledThreadPool() {
        int newScheduledCoreSize = 4;
        //newScheduledThreadPool有n和核心线程数，线程数量无上限，一旦非核心线程执行完毕就会立即被回收
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
//        executorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("延迟3秒执行一次的线程");
//            }
//        }, 3, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟3秒每隔2秒执行一次的线程");
            }
        }, 3, 2, TimeUnit.SECONDS);

    }

    private static void testNewSingleThreadExecutor() {
        //创建一个核心线程数和总线程数都是1的线程池
        //并且超时时间为0
        //它永远只有一个线程，有一个队列等待，所有加入的任务都在这个队列里面排队
        ExecutorService executorService = Executors.newSingleThreadExecutor();
    }

    private static void threadPoorTest() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                4, //最大核心数量
                10, //最大线程数量
                30,//非核心线程超时存活时间
                TimeUnit.SECONDS,//超时单位
                new LinkedBlockingQueue<>()//队列类型
        );
        //allowCoreThreadTimeOut(false)默认，核心线程不管怎么样都不会被销毁
        //allowCoreThreadTimeOut(false)，核心线程在超时之后，也会被销毁
        executorService.allowCoreThreadTimeOut(false);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("execute的方式加入ThreadPool");
            }
        });
        Future<Integer> future = executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                System.out.println("submit的方式加入ThreadPool");
                return 100;
            }
        });
        Integer callReturn = future.get();
        System.out.println("callReturn = " + callReturn);
    }
}
