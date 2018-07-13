package com.smc.androidbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.smc.androidbase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/12
 * @description 扇形布局
 */

public class SectorLayout extends ViewGroup {

    private final static String TAG = "SectorLayout";
    private final static double RADIAN_GAP = Math.toRadians(45);

    private final static double RADIAN_0 = Math.toRadians(0);
    private final static double RADIAN_90 = Math.toRadians(90);
    private final static double RADIAN_180 = Math.toRadians(180);
    private final static double RADIAN_270 = Math.toRadians(270);
    private final static double RADIAN_360 = Math.toRadians(360);

    private double mStartRadianOffset = Math.toRadians(-22.5);
    private int mXOffset = 0;
    private int mYOffset = 0;
    private int mMaxWidth = 0;
    private int mMaxHeight = 0;
    private int mRadius = 200;
    private List<Double> mRadianList = new ArrayList<>();//弧度
    private List<Point> mOriginList = new ArrayList<>();
    private List<Rect> mChildRectList = new ArrayList<>();

    public SectorLayout(Context context) {
        this(context, null);
    }

    public SectorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SectorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SectorLayout);
        mRadius = a.getDimensionPixelSize(R.styleable.SectorLayout_radius, -1);
        a.recycle();
        init();
    }

    public void setRadius(int radius) {
        mRadius = radius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            //第一次遍历子View,主要是找出各个点，xy坐标 超出画布的最大值，将在下一次遍历时进行矫正
            View child = getChildAt(i);
            LayoutParams lp = child.getLayoutParams();
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            if (i > 0) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                Point originPoint = mOriginList.get(i - 1);
                Rect rectChild = getRectByOrigin(originPoint.x, originPoint.y, childWidth, childHeight);
                if (rectChild.left < 0 && Math.abs(rectChild.left) > mXOffset) {
                    mXOffset = Math.abs(rectChild.left);
                }
                if (rectChild.top < 0 && Math.abs(rectChild.top) > mYOffset) {
                    mYOffset = Math.abs(rectChild.top);
                }
            }
        }

        for (int i = 0; i < getChildCount(); i++) {
            //第二次遍历子View,根据第一次遍历时计算得出的超出画布最大值，把这些偏移量加到各个子View中去
            //偏移之后，计算出最靠右View的right，以及最靠底View的bottom值，这个值就是布局需要的最大Width和Height
            //如果在xml文件中设置with或height为wrap_content时，就需要上述两个最大值
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            Log.d(TAG, "childWidth = " + childWidth + ", childHeight = " + childHeight);
            Rect rectChild;
            if (0 == i) {//第一个绘制在中间
                rectChild = getRectByOrigin(mRadius + mXOffset, mRadius + mYOffset, childWidth, childHeight);
            } else {
                Point originPoint = mOriginList.get(i - 1);
                rectChild = getRectByOrigin(originPoint.x + mXOffset, originPoint.y + mYOffset,
                        childWidth, childHeight);
            }
            mChildRectList.add(rectChild);
            mMaxWidth = Math.max(mMaxWidth, rectChild.right);
            mMaxHeight = Math.max(mMaxHeight, rectChild.bottom);
        }

        View firstChild = getChildAt(0);
        Rect rectFirst = getRectByOrigin(mRadius + mXOffset, mRadius + mYOffset,
                firstChild.getMeasuredWidth(), firstChild.getMeasuredHeight());
        mMaxWidth = Math.max(mMaxWidth, rectFirst.right);
        mMaxHeight = Math.max(mMaxHeight, rectFirst.bottom);

        //如果设置成wrap_content的话，把宽高最大值，设置下去
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, widthMode);
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, heightMode);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = mChildRectList.get(i);
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    private void init() {
        initRadianList();
        initOriginList();
    }

    private void initRadianList() {
        double radian = mStartRadianOffset;
        while (radian < RADIAN_360) {
            mRadianList.add(radian);
            radian += RADIAN_GAP;
        }
    }

    private void initOriginList() {
        int x = 0;
        int y = 0;
        for (double radian : mRadianList) {
            if (radian < RADIAN_0) {
                radian += RADIAN_360;
            }
            if (RADIAN_0 <= radian && radian < RADIAN_90) {
                x = (int) ((1 - Math.sin(radian)) * mRadius);
                y = (int) ((1 - Math.cos(radian)) * mRadius);
            } else if (RADIAN_90 <= radian && radian < RADIAN_180) {
                x = (int) ((1 + Math.sin((radian - RADIAN_90))) * mRadius);
                y = (int) ((1 - Math.cos((radian - RADIAN_90))) * mRadius);
            } else if (RADIAN_180 <= radian && radian < RADIAN_270) {
                x = (int) ((1 + Math.cos((radian - RADIAN_180))) * mRadius);
                y = (int) ((1 + Math.sin((radian - RADIAN_180))) * mRadius);
            } else {
                x = (int) ((1 - Math.sin((radian - RADIAN_270))) * mRadius);
                y = (int) ((1 + Math.cos((radian - RADIAN_270))) * mRadius);
            }
            Point point = new Point(x, y);
            mOriginList.add(point);
        }
    }

    private Rect getRectByOrigin(int x, int y, int width, int height) {
        int left = x - width / 2;
        int top = y - height / 2;
        Rect rect = new Rect(left, top, left + width, top + height);
        return rect;
    }

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
