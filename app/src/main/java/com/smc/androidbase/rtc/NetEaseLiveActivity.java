package com.smc.androidbase.rtc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.AVChatManagerLite;
import com.netease.nimlib.sdk.avchat.AVChatStateObserverLite;
import com.netease.nimlib.sdk.avchat.constant.AVChatChannelProfile;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoCapturerFactory;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;
import com.smc.androidbase.R;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class NetEaseLiveActivity extends Activity {

    private static final String TAG = NetEaseLiveActivity.class.getSimpleName();

    @BindView(R.id.surfaceview1)
    AVChatSurfaceViewRenderer mSurfaceview1;
    @BindView(R.id.surfaceview2)
    AVChatSurfaceViewRenderer mSurfaceview2;

    private AVChatStateObserverLite mAVChatStateObserverLite = new AVChatStateObserverLite() {
        @Override
        public void onJoinedChannel(int code, String audioFile, String videoFile, int elapsed) {

        }

        @Override
        public void onUserJoined(String account) {
            Log.d(TAG, "onUserJoined account=" + account);
            boolean setRemoteRenderSuccess = AVChatManager.getInstance().setupRemoteVideoRender(account, mSurfaceview2, false, AVChatVideoScalingType.SCALE_ASPECT_FILL);
            Log.d(TAG, "onUserJoined setRemoteRenderSuccess=" + setRemoteRenderSuccess);
        }

        @Override
        public void onUserLeave(String account, int event) {
            Log.d(TAG, "onUserLeave account=" + account);
//            AVChatManager.getInstance().setupRemoteVideoRender(account, null, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
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
    };

    public static void launch(Context context, String roomName) {
        Intent intent = new Intent(context, NetEaseLiveActivity.class);
        intent.putExtra("roomName", roomName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netease_live);
        ButterKnife.bind(this);
        String roomName = getIntent().getStringExtra("roomName");

        AVChatManagerLite.getInstance().enableRtc();
        AVChatManagerLite.getInstance().setChannelProfile(AVChatChannelProfile.CHANNEL_PROFILE_DEFAULT);
        AVChatManagerLite.getInstance().enableVideo();
        AVChatManagerLite.getInstance().setupVideoCapturer(AVChatVideoCapturerFactory.createCameraCapturer());
        AVChatManagerLite.getInstance().setupLocalVideoRender(mSurfaceview1, false, AVChatVideoScalingType.SCALE_ASPECT_FILL);
        boolean previewSuccess = AVChatManagerLite.getInstance().startVideoPreview();
        Log.d(TAG, "previewSuccess=" + previewSuccess);
        AVChatManagerLite.getInstance().joinRoom2(roomName, AVChatType.VIDEO, new AVChatCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData avChatData) {
                Log.d(TAG, "joinRoom2 onSuccess()");


            }

            @Override
            public void onFailed(int code) {
                Log.d(TAG, "joinRoom2 onFailed() code=" + code);
            }

            @Override
            public void onException(Throwable exception) {
                Log.d(TAG, "joinRoom2 onException()");
            }
        });
        AVChatManagerLite.getInstance().observeAVChatState(mAVChatStateObserverLite, true);
    }
}
