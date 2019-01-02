package com.smc.androidbase.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class MediaCodecActivity extends Activity implements SurfaceHolder.Callback {

    private final static String TAG = MediaCodecActivity.class.getSimpleName();
    private final static String SAMPLE = "sdcard/xiong.mp4";

    private WorkThread mWorkThread;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MediaCodecActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceView surfaceView = new SurfaceView(this);
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().addCallback(this);
        setContentView(surfaceView);
        File file = new File(SAMPLE);
        Log.d(TAG, "SAMPLE=" + SAMPLE);
        Log.d(TAG, "File.exists=" + file.exists());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
        if (null == mWorkThread) {
            mWorkThread = new WorkThread(holder.getSurface());
            mWorkThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private class WorkThread extends Thread {

        private MediaExtractor mMediaExtractor;
        private MediaCodec mMediaCodec;
        private Surface mSurface;

        public WorkThread(Surface surface) {
            this.mSurface = surface;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run() {
            super.run();
            mMediaExtractor = new MediaExtractor();
            try {
                mMediaExtractor.setDataSource(SAMPLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < mMediaExtractor.getTrackCount(); i++) {
                MediaFormat mediaFormat = mMediaExtractor.getTrackFormat(i);
                String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                Log.d(TAG, "mime=" + mime);
                //选择video轨道
                if (mime.startsWith("video/")) {
                    //初始化MediaCodec
                    mMediaExtractor.selectTrack(i);
                    try {
                        mMediaCodec = MediaCodec.createDecoderByType(mime);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mMediaCodec.configure(mediaFormat, mSurface, null, 0);
                    break;
                }
            }
            if (null == mMediaCodec) {
                return;
            }
            mMediaCodec.start();//开启MediaCodec
            ByteBuffer[] inputBuffers = mMediaCodec.getInputBuffers();
            ByteBuffer[] outputBuffers = mMediaCodec.getOutputBuffers();

            boolean isEos = false;
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            long startMs = System.currentTimeMillis();

            //死循环往Codec里面塞数据，以及从Codec里面后去数据
            while (!Thread.interrupted()) {
                //写入数据给MediaCodec
                if (!isEos) {
                    //获取inputBuffer 的 index（操作句柄）
                    int inputIndex = mMediaCodec.dequeueInputBuffer(10000);
                    Log.d(TAG, "inputIndex=" + inputIndex);
                    if (inputIndex >= 0) {
                        ByteBuffer buffer = inputBuffers[inputIndex];
                        //从MediaExtractor中读取原始数据，放入到buffer中去
                        int sampleSize = mMediaExtractor.readSampleData(buffer, 0);
                        if (sampleSize < 0) {
                            //sampleSize < 0表示已经没有可以输入的原始数据了，所以就写入一个EOS的消息给MediaCodec
                            mMediaCodec.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                            isEos = true;
                        } else {
                            mMediaCodec.queueInputBuffer(inputIndex, 0, sampleSize, mMediaExtractor.getSampleTime(), 0);
                            mMediaExtractor.advance();//表示下次read就是读取的下一个数据
                        }
                    }
                }

                //outputIndex就是解码完，输出buffer的index
                int outputIndex = mMediaCodec.dequeueOutputBuffer(bufferInfo, 10000);
                Log.d(TAG, "bufferInfo.presentationTimeUs=" + bufferInfo.presentationTimeUs +
                        ", bufferInfo.flags=" + bufferInfo.flags);
                switch (outputIndex) {
                    case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
                        Log.d(TAG, "INFO_OUTPUT_BUFFERS_CHANGED");
                        outputBuffers = mMediaCodec.getOutputBuffers();
                        break;
                    case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                        Log.d(TAG, "INFO_OUTPUT_FORMAT_CHANGED");
                        break;
                    case MediaCodec.INFO_TRY_AGAIN_LATER:
                        Log.d(TAG, "INFO_TRY_AGAIN_LATER");
                        break;
                    default:
                        //拿到输入的buffer
                        ByteBuffer buffer = outputBuffers[outputIndex];

                        //如果当前时间还没有到达时间戳的点，就会sleep(10)来等待
                        while (bufferInfo.presentationTimeUs / 1000 > System.currentTimeMillis() - startMs) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //释放当前buffer的index，然后并拿去渲染
                        mMediaCodec.releaseOutputBuffer(outputIndex, true);
                        break;
                }

                //当bufferInfo.flags的标志位是EOS的时候，就结束
                if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    break;
                }
            }
            mMediaCodec.start();
            mMediaCodec.release();
            mMediaExtractor.release();
        }
    }

}
