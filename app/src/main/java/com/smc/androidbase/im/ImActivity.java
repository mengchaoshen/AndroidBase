package com.smc.androidbase.im;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.smc.androidbase.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/25
 * @description
 */

public class ImActivity extends Activity {

    private final static String TAG = ImActivity.class.getSimpleName();

    @BindView(R.id.tv_login_8888)
    TextView mTvLogin8888;
    @BindView(R.id.tv_login_6666)
    TextView mTvLogin6666;
    @BindView(R.id.tv_send_to_8888)
    TextView mTvSendTo8888;
    @BindView(R.id.tv_send_to_6666)
    TextView mTvSendTo6666;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ImActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        ButterKnife.bind(this);
        registerMessageReceive();
    }

    @OnClick({R.id.tv_login_8888, R.id.tv_login_6666, R.id.tv_login_9999, R.id.tv_send_to_8888, R.id.tv_send_to_6666
            , R.id.tv_send_to_9999})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_8888:
                doLogin("8888");
                break;
            case R.id.tv_login_6666:
                doLogin("6666");
                break;
            case R.id.tv_login_9999:
                doLogin("9999");
                break;
            case R.id.tv_send_to_8888:
                sendMessage("8888");
                break;
            case R.id.tv_send_to_6666:
                sendMessage("6666");
                break;
            case R.id.tv_send_to_9999:
                sendMessage("9999");
                break;
        }
    }

    public void doLogin(String username) {
        LoginInfo info = new LoginInfo(username, "123456"); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        Log.d(TAG, "doLogin onSuccess");
                        mTvContent.setText(mTvContent.getText().toString() + "，" + param.getAccount() + "登录成功");
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.d(TAG, "doLogin onFailed code = " + code);
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Log.d(TAG, "doLogin onException exception = " + exception.getMessage());
                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }

    public void sendMessage(String toAccount) {
        MessageBean messageBean = new MessageBean("8723", "8888", "客户8888");
        String text = new Gson().toJson(messageBean);

        // 该帐号为示例，请先注册
        String account = toAccount;
        // 以单聊类型为例
        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
        // 创建一个文本消息
        IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType, text);
        // 发送给对方
        NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
    }

    public void registerMessageReceive() {
        // 监听消息状态变化
//        NIMClient.getService(MsgServiceObserve.class).observeMsgStatus(statusObserver, true);

        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(new Observer<List<IMMessage>>() {
            @Override
            public void onEvent(List<IMMessage> imMessages) {
                for (IMMessage imMessage : imMessages) {
                    String content = imMessage.getContent();
                    String fromAccount = imMessage.getFromAccount();
                    Log.d(TAG, "onEvent "
                            + ", content = " + content
                            + ", fromAccount = " + fromAccount
                    );

                    mTvContent.setText(mTvContent.getText().toString() + "，收到消息：" + content);
                }
            }
        }, true);
    }

    private Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage msg) {
            // 1、根据sessionId判断是否是自己的消息
            // 2、更改内存中消息的状态
            // 3、刷新界面
            String content = msg.getContent();
            String fromAccount = msg.getFromAccount();
            Log.d(TAG, "onEvent "
                    + ", content = " + content
                    + ", fromAccount = " + fromAccount
            );

            mTvContent.setText(mTvContent.getText().toString() + "，收到消息：" + content);
        }
    };

}
