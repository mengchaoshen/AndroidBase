package com.smc.androidbase.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/21
 * @description 方形的ImageView 采用重写onMeasure 调用setMeasuredDimension传入计算好的宽高
 */
public class SquareImageView extends AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int squareSize = Math.min(width, height);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.makeMeasureSpec(squareSize, mode);
        setMeasuredDimension(size, size);

    }
}
