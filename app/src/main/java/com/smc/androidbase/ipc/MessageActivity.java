package com.smc.androidbase.ipc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smc.androidbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/2
 * @description
 */

public class MessageActivity extends Activity {

    @BindView(R.id.tv_bind)
    TextView mTvBind;
    @BindView(R.id.tv_sendData)
    TextView mTvSendData;

    private Messenger mServiceMessenger;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Toast.makeText(MessageActivity.this, "客户端，收到消息了,服务端说:" + msg.getData().get("key"), Toast.LENGTH_SHORT).show();
        }
    };

    private Messenger mMessenger = new Messenger(mHandler);

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceMessenger = new Messenger(service);
            Toast.makeText(MessageActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MessageActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_bind, R.id.tv_sendData})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bind:
                Intent intent = new Intent(this, MessageService.class);
                bindService(intent, mConnection, BIND_AUTO_CREATE);
                break;
            case R.id.tv_sendData:
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("key", "我是客户端123，发消息");
                msg.setData(bundle);
                msg.replyTo = mMessenger;
                try {
                    mServiceMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
