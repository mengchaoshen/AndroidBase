package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ScreenRecorderActivity extends Activity {

    private final static String TAG = ScreenRecorderActivity.class.getSimpleName();
    private final static int REQUEST_CODE = 11;
    @BindView(R.id.tv_start)
    TextView mTvStart;

    private MediaRecorder mMediaRecorder;
    private String mRecordFilePath;
    private int mRecordWidth = 720;
    private int mRecordHeight = 1280;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_recorder);
        ButterKnife.bind(this);

        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (mediaProjectionManager != null) {
            Intent intent = mediaProjectionManager.createScreenCaptureIntent();
            PackageManager packageManager = getPackageManager();
            if (packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                //存在录屏授权的Activity
                startActivityForResult(intent, REQUEST_CODE);
            } else {
                Toast.makeText(this, "不能录制", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                setUpMediaRecorder();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "录屏权限已经被拒绝");
        }
    }

    //    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpMediaRecorder() {
        mRecordFilePath = "sdcard" + File.separator + System.currentTimeMillis() + ".mp4";
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        //设置音频来源
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置视频来源
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        //输出的录屏文件格式
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //录屏文件路径
        mMediaRecorder.setOutputFile(mRecordFilePath);
        //视频尺寸
        mMediaRecorder.setVideoSize(mRecordWidth, mRecordHeight);
        //音视频编码器
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //比特率
        mMediaRecorder.setVideoEncodingBitRate((int) (mRecordWidth * mRecordHeight * 3.6));
        //视频帧率
        mMediaRecorder.setVideoFrameRate(20);
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tv_start, R.id.tv_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                mMediaRecorder.start();
                break;
            case R.id.tv_stop:
                mMediaRecorder.stop();
                break;
            default:
                break;
        }

    }
}
