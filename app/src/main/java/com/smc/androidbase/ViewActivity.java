package com.smc.androidbase;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.smc.androidbase.view.CoinView;
import com.smc.androidbase.view.LoadingView;
import com.smc.androidbase.view.MyView;
import com.smc.androidbase.view.SquareImageView;

import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/25
 * @description
 */

public class ViewActivity extends Activity {

    @BindView(R.id.iv_square)
    SquareImageView mIvSquare;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.coin_view)
    CoinView mCoinView;
    @BindView(R.id.load_view)
    LoadingView mLoadView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ViewActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        mCoinView.startAnimation();
        setObjRotation();

        MyView myView = null;
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        GL10 gl10 = (GL10) EGLContext.getEGL();
    }

    private void setObjRotation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLoadView, "rotationY", 0, 360);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.start();
    }
}
