package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 */
public class SurfaceActivity extends Activity {

    @BindView(R.id.sv_1)
    SurfaceView mSv1;
    @BindView(R.id.sv_2)
    SurfaceView mSv2;
    @BindView(R.id.rl_root)
    RelativeLayout mRlRoot;
    @BindView(R.id.rl_surface_root)
    RelativeLayout mRlSurfaceRoot;

    SurfaceHolder mSurfaceHolder1;
    SurfaceHolder mSurfaceHolder2;
    SurfaceHolder mSurfaceHolder3;
    @BindView(R.id.iv_1)
    ImageView mIv1;
    @BindView(R.id.iv_2)
    ImageView mIv2;
    @BindView(R.id.rl_image_root)
    RelativeLayout mRlImageRoot;
    @BindView(R.id.sv_3)
    SurfaceView mSv3;
    @BindView(R.id.tv_1)
    TextView mTv1;
    @BindView(R.id.tv_2)
    TextView mTv2;
    @BindView(R.id.tv_3)
    TextView mTv3;


    public static void launch(Context context) {
        Intent intent = new Intent(context, SurfaceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);
        ButterKnife.bind(this);

        change1();

        mSv1.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mSurfaceHolder1 = holder;
                Canvas canvas1 = mSurfaceHolder1.lockCanvas();
                canvas1.drawColor(getColor(R.color.colorPrimary));
                mSurfaceHolder1.unlockCanvasAndPost(canvas1);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        mSv2.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mSurfaceHolder2 = holder;
                Canvas canvas2 = mSurfaceHolder2.lockCanvas();
                canvas2.drawColor(getColor(R.color.colorAccent));
                mSurfaceHolder2.unlockCanvasAndPost(canvas2);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        mSv3.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mSurfaceHolder3 = holder;
                Canvas canvas3 = mSurfaceHolder3.lockCanvas();
                canvas3.drawColor(getColor(R.color.black));
                mSurfaceHolder3.unlockCanvasAndPost(canvas3);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });


    }

//    @OnClick({R.id.sv_1, R.id.sv_2})
//    public void onClick(View view) {
////        RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) mSv1.getLayoutParams();
////        lp1.width = 600;
////        lp1.height = 1200;
////        mSv1.setLayoutParams(lp1);
////
////        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) mSv2.getLayoutParams();
////        lp2.width = 1200;
////        lp2.height = 600;
////        mSv2.setLayoutParams(lp2);
//
//        mSv1.setZOrderMediaOverlay(true);
//        mSv2.setZOrderMediaOverlay(false);
//
//        View child = mRlSurfaceRoot.getChildAt(0);
//        mRlSurfaceRoot.bringChildToFront(child);
//    }

//    @OnClick({R.id.iv_1, R.id.iv_2, R.id.sv_1, R.id.sv_2})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.sv_1:
//            case R.id.sv_2:
//                RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) mSv1.getLayoutParams();
//                lp1.width++;
//                lp1.height++;
//                mSv1.setLayoutParams(lp1);
//
//                RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) mSv2.getLayoutParams();
//                lp2.width++;
//                lp2.height++;
//                mSv2.setLayoutParams(lp2);
//                switch (view.getId()) {
//                    case R.id.sv_1:
////                        mSv1.setZOrderMediaOverlay(true);
////                        mSv2.setZOrderMediaOverlay(false);
//
//                        mSv1.setZOrderMediaOverlay(false);
//                        mSv2.setZOrderMediaOverlay(true);
//                        break;
//                    case R.id.sv_2:
////                        mSv1.setZOrderMediaOverlay(false);
////                        mSv2.setZOrderMediaOverlay(true);
//
//                        mSv1.setZOrderMediaOverlay(true);
//                        mSv2.setZOrderMediaOverlay(false);
//                        break;
//                }
////                mRlSurfaceRoot.bringChildToFront(view);
//                break;
//            case R.id.iv_1:
//                mRlImageRoot.bringChildToFront(view);
//                change1();
//                break;
//            case R.id.iv_2:
//                mRlImageRoot.bringChildToFront(view);
//                change2();
//                break;
//        }
//    }

    private void change1() {
        RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) mSv1.getLayoutParams();
        lp1.width = 900;
        lp1.height = 900;
        lp1.topMargin = 0;
        mSv1.setLayoutParams(lp1);

        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) mSv2.getLayoutParams();
        lp2.width = 300;
        lp2.height = 300;
        lp2.topMargin = 0;
        mSv2.setLayoutParams(lp2);

        RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) mSv3.getLayoutParams();
        lp3.width = 300;
        lp3.height = 300;
        lp3.topMargin = 300;
        mSv3.setLayoutParams(lp3);

        mSv1.setZOrderMediaOverlay(false);
        mSv2.setZOrderMediaOverlay(true);
        mSv3.setZOrderMediaOverlay(true);
    }

    private void change2() {
        RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) mSv1.getLayoutParams();
        lp1.width = 300;
        lp1.height = 300;
        lp1.topMargin = 0;
        mSv1.setLayoutParams(lp1);

        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) mSv2.getLayoutParams();
        lp2.width = 900;
        lp2.height = 900;
        lp2.topMargin = 0;
        mSv2.setLayoutParams(lp2);

        RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) mSv3.getLayoutParams();
        lp3.width = 300;
        lp3.height = 300;
        lp3.topMargin = 300;
        mSv3.setLayoutParams(lp3);

        mSv1.setZOrderMediaOverlay(true);
        mSv2.setZOrderMediaOverlay(false);
        mSv3.setZOrderMediaOverlay(true);
    }

    private void change3() {
        RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) mSv1.getLayoutParams();
        lp1.width = 300;
        lp1.height = 300;
        lp1.topMargin = 0;
        mSv1.setLayoutParams(lp1);

        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) mSv2.getLayoutParams();
        lp2.width = 300;
        lp2.height = 300;
        lp2.topMargin = 300;
        mSv2.setLayoutParams(lp2);

        RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) mSv3.getLayoutParams();
        lp3.width = 900;
        lp3.height = 900;
        lp3.topMargin = 0;
        mSv3.setLayoutParams(lp3);

        mSv1.setZOrderMediaOverlay(true);
        mSv2.setZOrderMediaOverlay(true);
        mSv3.setZOrderMediaOverlay(false);
    }

    @OnClick({R.id.tv_1, R.id.tv_2, R.id.tv_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                change1();
                break;
            case R.id.tv_2:
                change2();
                break;
            case R.id.tv_3:
                change3();
                break;
        }
    }
}
