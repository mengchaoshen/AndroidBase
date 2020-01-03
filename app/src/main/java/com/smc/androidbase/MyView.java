package com.smc.androidbase;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author shenmengchao
 * @version 1.0.0
 */
public class MyView extends View {

    private final static String TAG = MyView.class.getSimpleName();

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.d(TAG, getId() + " before width=" + getMeasureSpecStr(widthMeasureSpec) + ", height="
//                + getMeasureSpecStr(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, getId() + " width=" + getMeasureSpecStr(widthMeasureSpec) + ", height="
                + getMeasureSpecStr(heightMeasureSpec) + ", "+getMeasuredWidth());
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
}
