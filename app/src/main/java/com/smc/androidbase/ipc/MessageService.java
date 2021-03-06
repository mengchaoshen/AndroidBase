package com.smc.androidbase.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/2
 * @description
 *
 * Messenger的方式来进行IPC，
 * 优势：简单方便
 * 劣势：
 * 1.使用了Handler来处理消息，只能同步执行，不能同时处理多条消息
 * 2.只能用来发送数据，不能调用服务端接口
 *
 */
public class MessageService extends Service {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Messenger client = msg.replyTo;

            Message newMessage = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("key", "这里是服务端，收到你消息了！");
            newMessage.setData(bundle);
            try {
                client.send(newMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    private Messenger mMessenger = new Messenger(mHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
