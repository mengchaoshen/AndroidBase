package com.smc.androidbase.arc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arcvideo.arcrtcsdk.ArcRtcInvite;
import com.arcvideo.arcrtcsdk.enums.SessionEventType;
import com.arcvideo.arcrtcsdk.listener.ArcRtcListener;
import com.arcvideo.arcrtcsdk.listener.ArcRtcMessageListener;
import com.google.gson.Gson;
import com.smc.androidbase.R;
import com.smc.androidbase.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class InviteMainActivity extends Activity implements ArcRtcMessageListener {

    private final static String TAG = "AndroidBase";

    @BindView(R.id.et_userId)
    EditText mEtUserId;
    @BindView(R.id.et_userName)
    EditText mEtUserName;
    @BindView(R.id.tv_start)
    TextView mTvStart;
    @BindView(R.id.et_toUserId)
    EditText mEtToUserId;

    ArcRtcInvite mArcRtcSdk;
    @BindView(R.id.tv_invite)
    TextView mTvInvite;
    @BindView(R.id.et_sessionId)
    EditText mEtSessionId;
    @BindView(R.id.tv_create)
    TextView mTvCreate;
    @BindView(R.id.tv_join)
    TextView mTvJoin;

    public static void launch(Context context) {
        Intent intent = new Intent(context, InviteMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_main);
        ButterKnife.bind(this);
        initArcRtcSDK();
    }

    private void initArcRtcSDK() {
        if (null == mArcRtcSdk) {
            //要使用邀请，需要使用ArcRtcInvite
            mArcRtcSdk = ArcRtcInvite.Factory.create(this);
        }
        //这边与正常初始化一致
        String imAddress = GlobalConstant.IM_ADDRESS;
        mArcRtcSdk.setValidateParams(GlobalConstant.ACCESS_KEY, GlobalConstant.SECRET, GlobalConstant.APP_KEY)
                .setProductId(GlobalConstant.PRODUCT_ID)
                .setAddress(imAddress)
//                .setAccountInfo(customerId, orgId, userId)
                .setMaxSessionLength(4)
                .enableLog(true);

        //调用消息接收回调 这样可以在主页收到邀请消息了
        mArcRtcSdk.setArcRtcMessageListener(this);
        mArcRtcSdk.setRtcListener(new ArcRtcListener() {
            @Override
            public void onSession(SessionEventType sessionEventType, String s) {
                switch (sessionEventType) {
                    case CREATE:
                        break;
                    case CONNECT:
                        ToastUtil.showToast("IM连接成功");
                        break;
                    default:
                        break;
                }
//                if (sessionEventType == SessionEventType.CREATE) {
//                    ToastUtil.showToast("IM连接成功");
//                }
                Log.d(TAG, "onSession sessionEventType=" + sessionEventType + ", S = " + s);
            }

            @Override
            public void onRecVideoData(String s) {

            }

            @Override
            public void onError(int i, int i1, String s) {

            }

            @Override
            public void onInfo(int i, String s) {

            }
        });
    }

    @Override
    public void onMessage(int code, String extInfo, String msg) {
        //这里是收到其他用户的消息
        ToastUtil.showToast("收到消息 code = " + code + " extInfo = " + extInfo + " msg = " + msg);
        Log.d(TAG, "收到消息 code = " + code + " extInfo = " + extInfo + " msg = " + msg);
        MessageBean messageBean = new Gson().fromJson(msg, MessageBean.class);
        if (null != messageBean) {
            switch (messageBean.getMsgtype()) {
                //收到邀请消息
                case MessageBean.MSG_TYPE_INVITE:
                    break;
                //收到邀请回复消息
                case MessageBean.MSG_TYPE_REPLY:
                    //对方同意加入会话
                    break;
                //对方邀请您加入会话，但是现在已经挂断了
                case MessageBean.MSG_TYPE_HANG_UP:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onMessageBack(int i, String s, String s1) {
        ToastUtil.showToast("onMessageBack code=" + i + ", s=" + s + ", s1=" + s1);
        Log.d(TAG, "onMessageBack code=" + i + ", s=" + s + ", s1=" + s1);
    }

    @Override
    public void onStatusChange(String s, int i, String s1) {

    }

    @OnClick({R.id.tv_start, R.id.tv_invite, R.id.tv_create, R.id.tv_join, R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                String toUserId1 = mEtToUserId.getText().toString();
                MessageHelper.sendMessage(toUserId1, "123");
                break;
            case R.id.tv_start:
                int customerId = GlobalConstant.IM_CUSTOMER_ID;
                String orgId = GlobalConstant.IM_ORG_ID;
                String userId = mEtUserId.getText().toString();
//                String userName = mEtUserName.getText().toString();
                mArcRtcSdk.setAccountInfo(customerId, orgId, userId);
                mArcRtcSdk.startIM();
                break;
            case R.id.tv_invite:
                String toUserId = mEtToUserId.getText().toString();
                MessageHelper.sendInviteMessage(toUserId, "123");
                break;
            case R.id.tv_create:
                AudioLiveActivity.launch(this, LiveBaseActivity.ENTER_TYPE_CREATE, 0);
                break;
            case R.id.tv_join:
                String sessionId = mEtSessionId.getText().toString();
                AudioLiveActivity.launch(this, LiveBaseActivity.ENTER_TYPE_JOIN, Integer.valueOf(sessionId));
                break;
        }
    }

}
