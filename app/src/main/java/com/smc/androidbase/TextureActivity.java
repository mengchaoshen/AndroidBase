package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.TextureView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 */
public class TextureActivity extends Activity {

    @BindView(R.id.tv_1)
    TextureView mTv1;
    @BindView(R.id.tv_2)
    TextureView mTv2;

    public static void launch(Context context) {
        Intent intent = new Intent(context, TextureActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture);
        ButterKnife.bind(this);

        mTv1.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Canvas canvas = mTv1.lockCanvas();
                canvas.drawColor(getColor(R.color.colorPrimary));
                mTv1.unlockCanvasAndPost(canvas);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });

        mTv2.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Canvas canvas = mTv2.lockCanvas();
                canvas.drawColor(getColor(R.color.colorAccent));
                mTv2.unlockCanvasAndPost(canvas);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

    @OnClick({R.id.tv_1, R.id.tv_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                mTv1.bringToFront();
                break;
            case R.id.tv_2:
                mTv2.bringToFront();
                break;
        }
    }
}
