package com.smc.androidbase.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.smc.androidbase.R;

/**
 * @author mumuji
 * @version 1.0.0
 * @date 2018/7/8
 * @description
 */

public class CoinView extends View {

    private Context mContext;
    private Paint mPaintFront, mPaintBack, mPaintCompose;

    public CoinView(Context context) {
        this(context, null);
    }

    public CoinView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }
//    {
//        setLayerType(LAYER_TYPE_SOFTWARE, null); // 硬件加速下 ComposeShader 不能使用两个同类型的 Shader
//
//        mPaintCompose = new Paint(Paint.ANTI_ALIAS_FLAG);
////        mPaintCompose.setStyle(Paint.Style.FILL);
////        mPaintCompose.setColor(Color.parseColor("#00000000"));
//        mPaintFront = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaintFront.setStyle(Paint.Style.FILL);
//        mPaintFront.setColor(Color.parseColor("#E91E63"));
//
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.mipmap.back);
//        Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.mipmap.middle);
//        Shader shader2 = new BitmapShader(bitmap2,Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//
//        Shader shader = new ComposeShader(shader1,shader2, PorterDuff.Mode.SRC_ATOP);
//        mPaintCompose.setShader(shader);
//        // 用 Paint.setShader(shader) 设置一个 ComposeShader
//        // Shader 1: BitmapShader 图片：R.drawable.batman
//        // Shader 2: BitmapShader 图片：R.drawable.batman_logo
//    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaintCompose = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaintCompose.setStyle(Paint.Style.FILL);
//        mPaintCompose.setColor(Color.parseColor("#00000000"));
        mPaintFront = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBack = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFront.setStyle(Paint.Style.FILL);
        mPaintFront.setColor(Color.parseColor("#E91E63"));

        //绘制底部紫色的
        Bitmap bitmapBack = BitmapFactory.decodeResource(getResources(), R.mipmap.back);
        Shader shaderBack = new BitmapShader(bitmapBack, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        canvas.drawBitmap(bitmapBack, 0, 0, mPaintBack);//底图

        //绘制中间亮条
        Bitmap bitmapMiddle = BitmapFactory.decodeResource(getResources(), R.mipmap.middle);
        Shader shaderMiddle = new BitmapShader(bitmapMiddle, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate(mValue, mValue);
        shaderMiddle.setLocalMatrix(matrix);
        mPaintCompose.setShader(shaderMiddle);
        canvas.drawCircle(50, 50, 50, mPaintCompose);//长条，是跟draw的圆叠加

        //绘制最上面玫红色的
        Bitmap bitmapFront = BitmapFactory.decodeResource(getResources(), R.mipmap.front);//最上面的小圆
        canvas.drawBitmap(bitmapFront, 10, 10, mPaintFront);
    }

    private int mValue;

    public void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(-50, 30);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValue = (int) animation.getAnimatedValue();
//                Log.e("animtor","left:"+mCoinView.getLeft());
//                Log.e("animtor","top:"+mCoinView.getTop());
//                Log.e("animtor","right:"+mCoinView.getRight());
//                Log.e("animtor","bottom:"+mCoinView.getBottom());
//                mCoinView.layout(mCoinView.getLeft()+mValue, mCoinView.getTop()+mValue, mCoinView.getWidth()+mCoinView.getLeft()+mValue,mCoinView.getHeight()+mCoinView.getTop()+mValue);
                invalidate();
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
    }

    public void startAnimation(float width) {
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,mWidth*width);
//        Log.e("Animation2","mWidth*width:"+mWidth*width);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mFgWidth = (float) animation.getAnimatedValue();
//                mPercent = mFgWidth/mWidth*100;
//                Log.e("Animation","mFgWidth:"+mFgWidth);
//                invalidate();
//            }
//        });
//        valueAnimator.setDuration(2000);
////        valueAnimator.setInterpolator(new LinearInterpolator());
////        valueAnimator.setStartDelay(300);
//        valueAnimator.start();
    }
}
