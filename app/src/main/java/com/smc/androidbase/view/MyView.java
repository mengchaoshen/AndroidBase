package com.smc.androidbase.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/20
 * @description
 */

public class MyView extends LinearLayout {


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        layout(0, 0, 0, 0);
        draw(null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //UNSPECIFIED 是指当前的测量模式大小未指定，可以超过父布局的大小，比如ListView和ScrollView，
        //一般用于系统view,自定义view中使用不到
        int m1 = MeasureSpec.UNSPECIFIED;
        //EXACTLY 是指确切的大小，如果view的width=100dp等具体数值或width=Match_Parent，会使用这个模式
        //父view会给子view一个确切的大小
        int m2 = MeasureSpec.EXACTLY;
        //AT_MOST 是指最大大小，父view会给子view一个布局最大值。子布局必须在这个大小内绘制，
        //view 的 width=Wrap_content时，会使用这个模式
        int m3 = MeasureSpec.AT_MOST;


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
