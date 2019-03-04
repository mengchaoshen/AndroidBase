package com.smc.androidbase.rtc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManagerLite;
import com.netease.nimlib.sdk.avchat.AVChatStateObserverLite;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;
import com.smc.androidbase.R;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class NetEaseMainActivity extends Activity {

    private final static String TAG = NetEaseMainActivity.class.getSimpleName();

    @BindView(R.id.et_roomName)
    EditText mEtRoomName;
    @BindView(R.id.tv_create_room)
    TextView mTvCreateRoom;
    @BindView(R.id.tv_join_room)
    TextView mTvJoinRoom;
    @BindView(R.id.et_userName)
    EditText mEtUserName;
    @BindView(R.id.tv_login)
    TextView mTvLogin;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, NetEaseMainActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netease_main);
        ButterKnife.bind(this);

    }

//    private SDKOptions options(){
//        SDKOptions options = new SDKOptions();
//        options.sdkStorageRootPath = "sdcard/";
//        return options;
//    }


    @OnClick({R.id.tv_create_room, R.id.tv_join_room, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_create_room:
                AVChatManagerLite.getInstance().createRoom(mEtRoomName.getText().toString(), "", new AVChatCallback<AVChatChannelInfo>() {
                    @Override
                    public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                        avChatChannelInfo.getTimetagMs();
                        Log.d(TAG, "createRoom onSuccess()");
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.d(TAG, "createRoom onFailed() code=" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Log.d(TAG, "createRoom onException()");
                    }
                });
                break;
            case R.id.tv_join_room:
                NetEaseLiveActivity.launch(this, mEtRoomName.getText().toString());
                break;
            case R.id.tv_login:
                LoginInfo loginInfo = new LoginInfo(mEtUserName.getText().toString(), "123456");
                NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback() {
                    @Override
                    public void onSuccess(Object param) {
                        Log.d(TAG, "login onSuccess()");
                        AVChatManagerLite.getInstance().observeAVChatState(new AVChatStateObserverLite() {
                            @Override
                            public void onJoinedChannel(int code, String audioFile, String videoFile, int elapsed) {

                            }

                            @Override
                            public void onUserJoined(String account) {

                            }

                            @Override
                            public void onUserLeave(String account, int event) {

                            }

                            @Override
                            public void onLeaveChannel() {

                            }

                            @Override
                            public void onProtocolIncompatible(int status) {

                            }

                            @Override
                            public void onDisconnectServer(int code) {

                            }

                            @Override
                            public void onNetworkQuality(String user, int quality, AVChatNetworkStats stats) {

                            }

                            @Override
                            public void onCallEstablished() {

                            }

                            @Override
                            public void onDeviceEvent(int code, String desc) {

                            }

                            @Override
                            public void onConnectionTypeChanged(int netType) {

                            }

                            @Override
                            public void onFirstVideoFrameAvailable(String account) {

                            }

                            @Override
                            public void onFirstVideoFrameRendered(String user) {

                            }

                            @Override
                            public void onVideoFrameResolutionChanged(String user, int width, int height, int rotate) {

                            }

                            @Override
                            public void onVideoFpsReported(String account, int fps) {

                            }

                            @Override
                            public boolean onVideoFrameFilter(AVChatVideoFrame frame, boolean maybeDualInput) {
                                return false;
                            }

                            @Override
                            public boolean onAudioFrameFilter(AVChatAudioFrame frame) {
                                return false;
                            }

                            @Override
                            public void onAudioDeviceChanged(int device) {

                            }

                            @Override
                            public void onReportSpeaker(Map<String, Integer> speakers, int mixedEnergy) {

                            }

                            @Override
                            public void onSessionStats(AVChatSessionStats sessionStats) {

                            }

                            @Override
                            public void onLiveEvent(int event) {

                            }
                        }, true);
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.d(TAG, "login onFailed() code=" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Log.d(TAG, "login onException() ");
                    }
                });
                break;
        }
    }
}
