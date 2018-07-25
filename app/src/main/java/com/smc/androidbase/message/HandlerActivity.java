package com.smc.androidbase.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smc.androidbase.R;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/5/31
 * @description
 */

public class HandlerActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String TAG = "HandlerActivity";

    public static void launch(Context context) {
        context.startActivity(new Intent(context, HandlerActivity.class));
    }

    private TextView mTvSendToChild, mTvSendToChild2;

    /**
     * 主线程的Handler在创建起来之前就有Looper，已经开启了Looper.loop()循环，一旦MessageQueue里面有数据，就是会取出Message
     * 调用Message.target(也就是Handler)的handleMessage()方法。
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(HandlerActivity.this, "这里是handleMessage()", Toast.LENGTH_SHORT).show();
        }
    };

    private Handler mHandler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    private Handler mChildHandler, mChildHandler2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        new Thread(new Runnable() {
            @Override
            public void run() {


                mHandler2.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

                //这里是子线程，可以在这里进行耗时操作。
                Message message = Message.obtain();
                message.what = 1;
                //这里调用了sendMessage()后，最终回去执行MessageQueue.enqueueMessage()，把message加入消息队列
                mHandler.sendMessage(message);


                //下面是主线程给子线程发消息的接收过程
                Looper.prepare();//这里是为了创建Looper对象，如果在主线程的话，系统会自己创建Looper对象

                Toast.makeText(HandlerActivity.this, "test", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "before Looper.loop()");
                //Looper.loop()方法必须在线程的最后调用，因为在方法里面有死循环，后面的方法将永远无法执行。
//                Looper.loop();
                mChildHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.d(TAG, "这里是子线程1，收到消息了。 msg.waht = " + msg.what);
                    }
                };

                //一个Thread可以有很多个Handler，但是只能有一个Looper和MessageQueue
                mChildHandler2 = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.d(TAG, "这里是子线程2，收到消息了。 msg.waht = " + msg.what);
                    }
                };
                //loop()方法是无限循环去获取MessageQueue里面的message，
                // loop()方法是在哪里调用的，这个message就是在哪里处理的，从而实现了线程的切换
                Looper.loop();

            }
        }).start();

        mTvSendToChild = findViewById(R.id.tv_send_to_child);
        mTvSendToChild.setOnClickListener(this);
        mTvSendToChild2 = findViewById(R.id.tv_send_to_child2);
        mTvSendToChild2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_to_child:
                mChildHandler.sendEmptyMessage(0);
                break;
            case R.id.tv_send_to_child2:
                mChildHandler2.sendEmptyMessage(0);
                break;
            default:
                break;
        }
    }
}
