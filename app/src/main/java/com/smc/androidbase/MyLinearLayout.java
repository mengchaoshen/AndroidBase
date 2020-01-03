package com.smc.androidbase;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author shenmengchao
 * @version 1.0.0
 */
public class MyLinearLayout extends LinearLayout {

    private final static String TAG = MyLinearLayout.class.getSimpleName();

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.d(TAG, "before width=" + getMeasureSpecStr(widthMeasureSpec) + ", height="
                + getMeasureSpecStr(heightMeasureSpec));

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View view = getChildAt(0);
        LayoutParams lp = (LayoutParams) view.getLayoutParams();


        Log.d(TAG, "after width=" + getMeasureSpecStr(widthMeasureSpec) + ", height="
                + getMeasureSpecStr(heightMeasureSpec) + ", weight=" + lp.weight + ", "+view.getMeasuredWidth());
    }

    private String getMeasureSpecStr(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        String modeStr = "";
        if (mode == MeasureSpec.UNSPECIFIED) {
            modeStr = "UNSPECIFIED";
        } else if (mode == MeasureSpec.EXACTLY) {
            modeStr = "EXACTLY";
        } else if (mode == MeasureSpec.AT_MOST) {
            modeStr = "AT_MOST";
        }
        return modeStr + "[" + size + "]";
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
