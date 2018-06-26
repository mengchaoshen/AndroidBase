package com.smc.androidbase.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/25
 * @description 自定义流式布局
 */

public class FluidLayout extends ViewGroup {


    public FluidLayout(Context context) {
        super(context);
    }

    public FluidLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FluidLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

}
