package com.smc.androidbase.animator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.smc.androidbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/6
 * @description
 */

public class AnimatorActivity extends Activity {

    @BindView(R.id.btn_scale)
    Button mBtnScale;
    @BindView(R.id.btn_alpha)
    Button mBtnAlpha;
    @BindView(R.id.btn_rotate)
    Button mBtnRotate;
    @BindView(R.id.btn_trans)
    Button mBtnTrans;
    @BindView(R.id.iv_scale)
    ImageView mIvScale;
    @BindView(R.id.iv_alpha)
    ImageView mIvAlpha;
    @BindView(R.id.iv_rotate)
    ImageView mIvRotate;
    @BindView(R.id.iv_trans)
    ImageView mIvTrans;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, AnimatorActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_scale, R.id.btn_alpha, R.id.btn_rotate, R.id.btn_trans})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scale:
                //Scale缩放可以配置的参数有，
                //fromXScale fromYScale toXScale toYScale 缩放的起始终止XY坐标百分比，
                //fromXScale=0.5，即表示起始是x坐标的一半
                //pivotX pivotY表示缩放的远中心点，
                //pivotX=50表示中心点的x坐标是50px pivotX=50%表示中心点的x坐标是目标width的50%
                //pivotX=50%p表示中心点的x坐标是目标view的父控件width的50%，p表示parent的意思
                //duration 表示这个动画执行的时间，但是是毫秒
                //duration=300 表示动画执行的时间是300ms
                //fillAfter=true表示动画结束以后，View会保持动画结束时的大小，默认是为false
                //fillBefore=true fillEnabled=true含义一样，都是在动画结束后，回复到起始状态，默认值为true
                //这里，如果fillAfter=true和fillBefore=true那么View会在动画结束会保持结束时的状态
                //repeatCount=1表示动画的循环次数，repeatCount=1时，动画的播放次数就是1+1=2次
                //repeatMode 表示动画重复模式 这个模式仅当repeatCount>0是起效果
                //repeatMode="reverse"表示倒叙播放 repeatMode="restart"表示重新播放
                Animation animationScale = AnimationUtils.loadAnimation(this, R.anim.scale);
                mIvScale.startAnimation(animationScale);
                break;
            case R.id.btn_alpha:
                Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
                mIvScale.startAnimation(animationAlpha);
                break;
            case R.id.btn_rotate:
                break;
            case R.id.btn_trans:
                break;
        }
    }
}
