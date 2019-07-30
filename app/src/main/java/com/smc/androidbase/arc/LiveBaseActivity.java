package com.smc.androidbase.arc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.arcvideo.arcrtcsdk.ArcRtcInvite;
import com.arcvideo.arcrtcsdk.ArcRtcSDK;
import com.arcvideo.arcrtcsdk.IBase;
import com.arcvideo.arcrtcsdk.bean.VideoInfo;
import com.arcvideo.arcrtcsdk.enums.AudioProfile;
import com.arcvideo.arcrtcsdk.enums.SessionEventType;
import com.arcvideo.arcrtcsdk.enums.VideoProfile;
import com.arcvideo.arcrtcsdk.listener.ArcRtcListener;
import com.arcvideo.arcrtcsdk.listener.ArcRtcMessageListener;
import com.arcvideo.arcrtcsdk.utils.ScreenUtil;
import com.arcvideo.rtcengine.helper.RtcEngineHelper;
import com.arcvideo.rtcmessage.RtcMessageConstant;
import com.arcvideo.rtcmessage.model.RtcMemberModel;
import com.arcvideo.rtcmessage.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smc.androidbase.utils.ToastUtil;

import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public abstract class LiveBaseActivity extends Activity {

    public final static String TAG = "AndroidBase";

//    public final static String LIVE_TYPE = "live_type";
//    public final static int LIVE_TYPE_DEFAULT = 0;
//    public final static int LIVE_TYPE_AUDIO_ONLY = 1;
//    public final static int LIVE_TYPE_VUDIO_ONLY = 2;

    public final static String ENTER_TYPE_CREATE = "enter_type_create";
    public final static String ENTER_TYPE_JOIN = "enter_type_join";
    public final static String ENTER_TYPE = "enter_type";
    public final static String SESSION_ID = "session_id";
    public final static String JUMP_TO_FEEDBACK = "jump_to_feedback";
    public final static String WARN_MESSAGE = "warn_message";

    protected ArcRtcInvite mArcRtcSDK;
    protected int mWindowWidth;
    protected int mWindowHeight;

    protected boolean mIsVoiceMute = false;
    protected String mEnterType;
    protected int mSessionId;
    private PhoneStateReceiver mPhoneStateReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "LiveActivity initActivity" + this);
        initParams();
        initSystemSetting();
        initArcRtcSDK();
        startEngine();
    }

    private void initParams() {
        if (TextUtils.isEmpty(mEnterType)) {
            mEnterType = getIntent().getStringExtra(ENTER_TYPE);
        }
        mSessionId = getIntent().getIntExtra(SESSION_ID, 0);
        mWindowWidth = ScreenUtil.getDisplayMetrics(this).widthPixels;
        mWindowHeight = ScreenUtil.getDisplayMetrics(this).heightPixels;
        LogUtil.d(TAG, "initParams() mWindowWidth = " + mWindowWidth + ", mWindowHeight = " + mWindowHeight);
    }

    private void initSystemSetting() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mPhoneStateReceiver = new PhoneStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(mPhoneStateReceiver, filter);

    }

    public class PhoneStateReceiver extends BroadcastReceiver {

        public final String TAG = PhoneStateReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "PhoneStateReceiver action: " + action);
            String resultData = this.getResultData();
            Log.d(TAG, "PhoneStateReceiver getResultData: " + resultData);

            if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                // 去电，可以用定时挂断
                // 双卡的手机可能不走这个Action
                String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                Log.d(TAG, "PhoneStateReceiver EXTRA_PHONE_NUMBER: " + phoneNumber);
            } else {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                Log.d(TAG, "PhoneStateReceiver onReceive state: " + state);
                if ("RINGING".equals(state)) {
//                    ToastUtil.showToast("有电话接入，已停止视频会议");
                    onBackPressed();
                }
            }
        }
    }

    private void initArcRtcSDK() {
        //获取ArcRtcSDK实例
        if (null == mArcRtcSDK) {
            mArcRtcSDK = ArcRtcInvite.Factory.create(this);
        }
//        UserInfo userInfoBean = UserDataCenter.getInstance().getUserInfoBean();
//        int mCustomerId = GlobalConstant.IM_CUSTOMER_ID;
//        String mImAddress = UserDataCenter.getInstance().getImAddress();
//        String mOrgId = GlobalConstant.IM_ORG_ID;
//        String mUserId = userInfoBean.getUnionid();
//        String nickname = userInfoBean.getNickname();
//        String iconUrl = userInfoBean.getHeadimgurl();
        mArcRtcSDK
                //鉴权参数的设置，由当虹云提供 必填
                .setValidateParams(GlobalConstant.ACCESS_KEY, GlobalConstant.SECRET, GlobalConstant.APP_KEY)
                //ProductId是产品ID，确保非空且各个平台的值一致就可以（String类型，无其他限制）必填
                .setProductId(GlobalConstant.PRODUCT_ID)
                //通话用户的昵称和头像，选填
//                .setUserInfo(nickname, iconUrl)
                //账号信息，CustomerId有当虹云提供，OrgId确保非空且各个平台的值一致就可以（Int类型，无其他限制）必填
//                .setAccountInfo(mCustomerId, mOrgId, mUserId)
                //role和mode 无其他特殊情况，参考demo即可，maxSessionLength为最大会话人数 必填
                .setConnectInfo(RtcMessageConstant.ROLE_DEFAULT, 0, getMaxSessionLength())
                //是否打开log日志功能，默认为false 选填
                .enableLog(true)
                //设置IM通讯地址，当虹提供 必填
//                .setAddress(mImAddress)
                //设置surfaceView
                //* 1.userId!=null  surfaceView!=null 表示surfaceView与userId的绑定或重新绑定
                //* 2.userId==null  surfaceView!=null 表示设置本地预览surfaceView
                //* 3.userId!=null  surfaceView==null 表示有用户离开房间，需要解绑surfaceView
                .setSurfaceView(null, getLocalSurfaceView());
        //设置surfaceView的列表 第一个为本地预览画面，第二个为最先加入视频通讯的用户，以此类推
        //setSurfaceView与setSurfaceViewList使用一个即可，建议使用setSurfaceViewList
//                .setSurfaceViewList(buildSurfaceViewList());
        AudioProfile audioProfile = buildAudioInfo();
        if (null != audioProfile) {
            //设置音频参数 有默认值选填
            mArcRtcSDK.setAudioInfo(buildAudioInfo());
        }
        VideoInfo videoInfo = buildVideoInfo();
        if (null != videoInfo) {
            //设置视频参数 有默认值选填
            mArcRtcSDK.setVideoInfo(buildVideoInfo());

            VideoInfo video = new VideoInfo();
            video.setVideoProfile(VideoProfile.RTC_VIDEO_PROFILE_PORTRAIT_720P);
            mArcRtcSDK.setVideoInfo(video);
        }

        //设置会议类型  0表示音视频 1表示纯语音 默认为1 选填
        mArcRtcSDK.setLiveMode(getLiveMode());

        //设置回调监听，后面有详细讲解
        mArcRtcSDK.setRtcListener(new ArcRtcListener() {
            @Override
            public void onSession(SessionEventType type, String userId) {
                doOnSessionEvent(type, userId);
            }

            @Override
            public void onRecVideoData(String userId) {
                doOnFirstFrame(userId);
            }

            @Override
            public void onInfo(int code, String message) {
                doOnInfo(code, message);
            }

            @Override
            public void onError(int mainCode, int subCode, String message) {
                doOnError(mainCode, subCode, message);
            }
        });
        //设置消息回调监听
        mArcRtcSDK.setArcRtcMessageListener(new ArcRtcMessageListener() {
            @Override
            public void onMessage(int code, String extInfo, String msg) {
                LogUtil.d(TAG, "onMessage()");
            }

            @Override
            public void onMessageBack(int code, String userId, String msg) {
                LogUtil.d(TAG, "onMessageBack()");
            }

            @Override
            public void onStatusChange(String cid, int status, String extInfo) {

            }
        });
        //初始化的最后一个步骤
        mArcRtcSDK.prepare();
    }

    private void startEngine() {
        if (mEnterType.equals(ENTER_TYPE_CREATE)) {
//            boolean encrypt = SettingDataCenter.getInstance().getEncrypt();
            //创建会话，encrypt表示是否加密
            mArcRtcSDK.createAndJoinSession(false);
        } else if (mEnterType.equals(ENTER_TYPE_JOIN)) {
            //加入会话，需要传递一个sessinId表示需要加入的会议Id
            mArcRtcSDK.joinSession(mSessionId);
        }
    }

    protected abstract int getLiveMode();

    protected abstract void doOnSessionEvent(SessionEventType type, String userId);

    protected abstract int getMaxSessionLength();

    protected void doOnFirstFrame(String cid) {
        LogUtil.d(TAG, "LiveActivity doOnFirstFrame cid:" + cid);
    }

    protected void doOnInfo(int code, String message) {
//        ToastUtil.showToast(message);
        LogUtil.d(TAG, "LiveActivity doOnInfo code = " + code);
    }

    protected void doOnError(int mainCode, int subCode, String message) {
        ToastUtil.showToast("出现异常 mainCode:" + mainCode + ", subCode:" + subCode + ",message:" + message);
    }

    protected SurfaceView getLocalSurfaceView() {
        return null;
    }

    protected List<SurfaceView> buildSurfaceViewList() {
        return null;
    }

    /**
     * 构建音频参数
     *
     * @return
     */
    protected AudioProfile buildAudioInfo() {
        return AudioProfile.RTC_AUDIO_OPUS_PROFILE1;
    }

    protected VideoInfo buildVideoInfo() {
        return null;
    }

//    protected void addUser() {
//        if (mArcRtcSDK.getSessionInfoList().size() >= getMaxSessionLength()) {
//            ToastUtil.showToast("房间人数已达上限，无法继续邀请");
//        } else {
//            shareToFriend();
//        }
//    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(JUMP_TO_FEEDBACK, true);
        setResult(0, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mPhoneStateReceiver);
        //Activity销毁之后，执行leaveSession退出会话和release释放sdk
        mArcRtcSDK.leaveSession();
        mArcRtcSDK.release();
        LogUtil.d(TAG, "onDestroy() End");
    }

}
