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
