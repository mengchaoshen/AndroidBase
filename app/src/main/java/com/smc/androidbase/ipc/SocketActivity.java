package com.smc.androidbase.ipc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.smc.androidbase.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/3
 * @description
 */

public class SocketActivity extends Activity {

    public final static String TAG = "SocketActivity";

    public final static int MSG_READY = 1;
    public final static int MSG_RECEIVE = 2;

    @BindView(R.id.tv_send)
    TextView mTvSend;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    private Socket mSocket;
    private PrintWriter mPrintWriter;
    private StringBuilder mStringBuilder = new StringBuilder();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_RECEIVE:
                    mStringBuilder.append(msg.obj).append("\n");
                    mTvContent.setText(mStringBuilder.toString());
                    break;
                default:
                    break;
            }
        }
    };

    private Handler mSocketHandler;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SocketActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);

        Intent intent = new Intent(this, SocketService.class);
        startService(intent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mSocketHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mPrintWriter.println("123");
                        Log.d(TAG, "发送消息成功：" + "123");
                    }
                };
                connectSocket();
                Looper.loop();
            }
        }).start();
    }

    @OnClick(R.id.tv_send)
    public void onClick() {
        if (null != mPrintWriter) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mPrintWriter.println("123");
                    Log.d(TAG, "客户端发送消息成功：" + "123");
                }
            }).start();
            mSocketHandler.sendEmptyMessage(0);
            mStringBuilder.append("123").append("\n");
            mTvContent.setText(mStringBuilder.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSocket) {
            try {
                mSocket.shutdownInput();
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void connectSocket() {

        //connect
        while (null == mSocket) {
            try {
                mSocket = new Socket("172.28.103.82", 8888);
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MSG_READY);
            } catch (IOException e) {
                Log.d(TAG, "connect error" + e.getMessage());
                e.printStackTrace();
            }
        }


        //receive message
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

            while (!isFinishing()) {
                String message = bufferedReader.readLine();
                if (!TextUtils.isEmpty(message)) {
                    Log.d(TAG, "客户端收到消息：" + message);
                    mHandler.obtainMessage(MSG_RECEIVE, message).sendToTarget();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
