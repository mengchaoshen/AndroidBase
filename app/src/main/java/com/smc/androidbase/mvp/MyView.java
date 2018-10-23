package com.smc.androidbase.mvp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/10/15
 * @description
 */

public class MyView extends LinearLayout implements IView {

    MyPresenter mMyPresenter = new MyPresenter(this);

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //通过mMyPresenter去获取数据
        mMyPresenter.getData();
    }


    @Override
    public void onGetDataSuccess(Object o) {
        //这里是数据获取成功后的回调
    }
}
