package com.smc.androidbase.view;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

import com.smc.androidbase.R;

/**
 * @author mumuji
 * @version 1.0.0
 * @date 2018/7/11
 * @description
 */

public class LoadingView extends View {

    private int mValue;
    private Paint mPaintBack, mPaintCompose;
    private Bitmap mBitmapShadow, mBitmapMasking;
    private Shader mShaderShadow, mShaderMasking;
    private ValueAnimator mValueAnimator;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//这个参数的含义是 仅获取图片的宽高，不去真正价值图片大小
        BitmapFactory.decodeResource(getResources(), R.mipmap.icon_loading_background2, options);
        int width = options.outWidth;//获取图片的宽
        int height = options.outHeight;//获取图片的高

        //获取宽高的测量模式
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //根据图片的宽高，合成宽高带测量模式的值
        int measureWidth = MeasureSpec.makeMeasureSpec(width, widthMeasureSpec);
        int measureHeight = MeasureSpec.makeMeasureSpec(height, heightMeasureSpec);
        //将测量完成之后的宽高，传递给super
        super.onMeasure(measureWidth, measureHeight);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setLayerType(LAYER_TYPE_SOFTWARE, null); // 硬件加速下 ComposeShader 不能使用两个同类型的 Shader

        mPaintCompose = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBack = new Paint(Paint.ANTI_ALIAS_FLAG);

        //阴影
        mBitmapShadow = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_loading_shadow2);
        mShaderShadow = new BitmapShader(mBitmapShadow, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //阴影图片移动
        Matrix matrix = new Matrix();
        matrix.setTranslate(mValue - 120, mValue - 120);
        mShaderShadow.setLocalMatrix(matrix);
        //阴影图片需要叠加的底图
        mBitmapMasking = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_loading_masking2);
        mShaderMasking = new BitmapShader(mBitmapMasking, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //将阴影原图跟底图叠加，得到需要显示的阴影
        Shader shader = new ComposeShader(mShaderShadow, mShaderMasking, PorterDuff.Mode.DST_IN);
        mPaintCompose.setShader(shader);
        //画底图1
        Bitmap bitmapBack = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_loading_background2);
        canvas.drawBitmap(bitmapBack, 0, 0, mPaintBack);
        //画底图2
        canvas.drawBitmap(mBitmapMasking, 0, 0, mPaintBack);
        //画阴影
        int width = bitmapBack.getWidth();
        int height = bitmapBack.getHeight();
        Rect rect = new Rect(0, 0, width, height);
        canvas.drawRect(rect, mPaintCompose);

    }

    public void startAnimation() {
        mValueAnimator = ValueAnimator.ofInt(0, 300, 0);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValue = (int) animation.getAnimatedValue();
            }
        });
        mValueAnimator.setDuration(1800);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.setEvaluator(new IntEvaluator());
        mValueAnimator.start();
    }

    public void stopAnimation() {
        mValueAnimator.cancel();
    }
}
