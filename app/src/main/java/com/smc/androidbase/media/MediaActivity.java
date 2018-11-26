package com.smc.androidbase.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.smc.androidbase.R;

import java.io.IOException;
import java.nio.ByteBuffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/11/2
 * @description
 */

public class MediaActivity extends Activity {

    private final static String TAG = MediaActivity.class.getSimpleName();
    @BindView(R.id.tv_start)
    TextView mTvStart;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MediaActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_start, R.id.tv_start_mediaCodec})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_start_mediaCodec:
                MediaCodecActivity.launch(this);
                break;
            case R.id.tv_start:
                MediaPlayer mediaPlayer = MediaPlayer.create(this, null);

                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Log.d(TAG, "onError() what:" + what + ", extra:" + extra);
                        return false;
                    }
                });
                mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        Log.d(TAG, "onBufferingUpdate() percent:" + percent);
                    }
                });
                try {
                    mediaPlayer.setDataSource("http://00i5v2lv.vod2.danghongyun.com/target/hls/2018/04/26/1091_778e69ddff2b41b88b6ea3724ab5a743_90_1280x720.m3u8");

                    SurfaceView surfaceView = new SurfaceView(MediaActivity.this);
                    surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                        @Override
                        public void surfaceCreated(SurfaceHolder holder) {

                        }

                        @Override
                        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                        }

                        @Override
                        public void surfaceDestroyed(SurfaceHolder holder) {

                        }
                    });
                    mediaPlayer.setDisplay(surfaceView.getHolder());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default :
                break;
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void testMediaCodec(){
//        try {
//            MediaCodec mediaCodec = MediaCodec.createByCodecName("");
//            MediaFormat videoFormat = MediaFormat.createVideoFormat("", 0, 0);
//            mediaCodec.configure(videoFormat, new Surface(null), null, 0);
//            mediaCodec.start();
//
//            //获取输入Buffer的index
//            int index = mediaCodec.dequeueInputBuffer(1000);
//
//            mediaCodec.queueInputBuffer();
//
//
//            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
//            //获取输出buffer的index
//            int outputIndex = mediaCodec.dequeueOutputBuffer(info, 1000);
//
//            //用来异步获取MediaCodec输入/输出的buffer
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                mediaCodec.setCallback(new MediaCodec.Callback() {
//                    @Override
//                    public void onInputBufferAvailable(@NonNull MediaCodec codec, int index) {
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                            ByteBuffer byteBuffer = codec.getInputBuffer(index);
//                            //将需要编解码的数据放入byteBuffer中
//                            codec.queueInputBuffer(index, ...);
//                        }
//
//                    }
//
//                    @Override
//                    public void onOutputBufferAvailable(@NonNull MediaCodec codec, int index, @NonNull MediaCodec.BufferInfo info) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            ByteBuffer byteBuffer = codec.getOutputBuffer(index);
//                            MediaFormat mediaFormat = codec.getOutputFormat(index);
//
//                            codec.releaseOutputBuffer(index, ..);//释放这个buffer
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull MediaCodec codec, @NonNull MediaCodec.CodecException e) {
//
//                    }
//
//                    @Override
//                    public void onOutputFormatChanged(@NonNull MediaCodec codec, @NonNull MediaFormat format) {
//
//                    }
//                });
//            }
//            mediaCodec.queueInputBuffer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
