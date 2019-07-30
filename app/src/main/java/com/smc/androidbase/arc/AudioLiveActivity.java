package com.smc.androidbase.arc;

import android.app.Activity;
import android.content.Intent;

import com.arcvideo.arcrtcsdk.enums.SessionEventType;
import com.arcvideo.commondef.ArcTypes;
import com.smc.androidbase.utils.ToastUtil;

//import com.arcvideo.arcrtcsdk.constant.ArcRtcType;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class AudioLiveActivity extends LiveBaseActivity {

    public  static final int REQUEST_CODE = 123;

    public static void launch(Activity activity, String enterType, int sessionId) {
        Intent intent = new Intent(activity, AudioLiveActivity.class);
        intent.putExtra(ENTER_TYPE, enterType);
        intent.putExtra(SESSION_ID, sessionId);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected int getLiveMode() {
        return ArcTypes.ARC_RTC_MODE_AUDIO;
    }

    @Override
    protected void doOnSessionEvent(SessionEventType type, String userId) {

        switch (type) {
            case CREATE:
                mSessionId = mArcRtcSDK.getSessionInfoList().get(0).getSessionid();
                ToastUtil.showToast("开始语音会议 " + mSessionId);
                break;
            case JOIN:
                ToastUtil.showToast(mSessionId + "");
                break;
            case REC_JOIN:
                ToastUtil.showToast("有人加入会话 id = " + userId);
                break;
            case REC_LEFT:
                ToastUtil.showToast("有人退出会话 id = " + userId);
                break;
            case BLACK_FRAME_ON:
                ToastUtil.showToast("有人已黑帧 id = " + userId);
                break;
            case BLACK_FRAME_OFF:
                ToastUtil.showToast("有人已打开画面 id = " + userId);
                break;
            case MUTE_ON:
                ToastUtil.showToast("有人已静音 id = " + userId);
                break;
            case MUTE_OFF:
                ToastUtil.showToast("有人已打开声音 = " + userId);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getMaxSessionLength() {
        return 20;
    }
}
