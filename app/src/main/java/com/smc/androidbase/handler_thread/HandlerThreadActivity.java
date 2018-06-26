package com.smc.androidbase.handler_thread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.smc.androidbase.R;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/7
 * @description
 */

public class HandlerThreadActivity extends Activity {

    private final static String TAG = "HandlerThreadActivity";

    private TextView mTextView, mTextView2;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private Handler mMainHandler = new Handler();

    private Looper mLooper;
    Thread mThread;
    private Object mObject = new Object();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, HandlerThreadActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);

        mTextView = findViewById(R.id.text_view);
        mTextView2 = findViewById(R.id.text_view2);
        Log.d(TAG, "this main = " + this);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "this click = " + this);
            }
        });
        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "this click2 = " + this);
            }
        });

        initThread();
//        initHandlerThread();
    }

    private void initThread() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    while(true){
//                        Thread.sleep(1200);
//                        mMainHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mTextView.setText("今日大盘指标 : " + (int)(Math.random() * 1000 + 2000));
//                            }
//                        });
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Looper.prepare();
                Log.d(TAG, "this1 = " + this);
                synchronized (mThread) {
                    Log.d(TAG, "this2 = " + this);
                    Log.d(TAG, "mThread = " + mThread);
                    mLooper = Looper.myLooper();
                    mThread.notifyAll();
                    Log.d(TAG, "looper get success 1");
                }
                Looper.loop();
            }
        });
        mThread.start();
        synchronized (mThread){
            while(null == mLooper){
                try {
                    mThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(TAG, "looper get success 2");
        mHandler = new Handler(mLooper) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG, "thread receive message");
            }
        };
        mHandler.sendEmptyMessage(0);
    }

    private void initHandlerThread() {
        //HandlerThread的作用其实和Thread差不多，都是创建一个子线程
        //它的好处就是，在里面引入了Looper和Handler,这样Looper.loop()就会一直执行，使得子线程不会被销毁
        //这样就免去了线程不断创建和销毁的过程
        mHandlerThread = new HandlerThread("hello");
        mHandlerThread.start();

//        mHandlerThread.getThreadHandler();

        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    Thread.sleep(1200);
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText("今日大盘指标 : " + (int) (Math.random() * 1000 + 2000));
                        }
                    });
                    mHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            mHandlerThread.quitSafely();
//        } else {
//            mHandlerThread.quit();
//        }
    }
}
