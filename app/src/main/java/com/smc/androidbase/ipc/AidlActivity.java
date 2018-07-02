package com.smc.androidbase.ipc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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

public class AidlActivity extends Activity {

    @BindView(R.id.tv_bind)
    TextView mTvBind;
    @BindView(R.id.tv_sendData)
    TextView mTvSendData;

    private IMyAidlInterface mAidlInterface;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, AidlActivity.class));
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            Toast.makeText(AidlActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_bind, R.id.tv_sendData})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bind:
                Intent intent = new Intent(this, AidlService.class);
                bindService(intent, mConnection, BIND_AUTO_CREATE);
                break;
            case R.id.tv_sendData:
                try {
                    int result = mAidlInterface.add(1, 1);
                    Toast.makeText(AidlActivity.this, "aidl方法调用结果是:" + result, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
