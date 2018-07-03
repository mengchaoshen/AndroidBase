package com.smc.androidbase.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/3
 * @description
 */

public class SocketService extends Service {

    public final static String TAG = "SocketService";
    private boolean mIsDestroy;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startSocketServer();
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsDestroy = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startSocketServer() {
        //connect
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Log.d(TAG, "服务开启成功");

            //receive
            while (!mIsDestroy) {
                final Socket socket = serverSocket.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                            while (!mIsDestroy) {
                                String text = null;
                                text = in.readLine();
                                Log.d(TAG, "服务端收到消息：" + text);
                                if (!TextUtils.isEmpty(text)) {
                                    out.println("456");
                                    Log.d(TAG, "服务端发送消息：" + "456");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
