package com.smc.androidbase.event_dispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/5
 * @description
 */

public class EventLinearLayout extends LinearLayout {

    private final static String TAG = "EventLinearLayout";

    public EventLinearLayout(Context context) {
        super(context);
    }

    public EventLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent() action = " + ev.getAction());
        //return true,表示这个事件被消费掉了，并不会执行当前View的onTouchEvent，也不会往上抛
        //但是接下来的事件还是会往这个View发送,比如down事件return true，后续up事件还是会往这里传递
//        return true;

//        //return false,表示这个事件不被消费，并且当前View不会执行OnTouchEvent,但是会交由父控件的OnTouchEvent处理
        //然后本次事件的其他action也不会再来这里，比如down事件return false，后面move up等事件都不会进入这里了。
//        return false;

        //在ViewGroup中调用super.dispatchTouchEvent()，会去执行onInterceptTouchEvent()
        //后续事件也会往这边传递
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent() action = " + ev.getAction());
//        return true；会拦截本次action，子View的各个dispatchTouchEvent(),onInterceptTouchEvent(),OnTouch()都不会收到action
       // 并交由该View的onTouchEvent()处理处个action,然后返回父控件去处理
//        return true;

        //return false与return super一样，不会拦截此次事件，也就是子View还是可以接收到action
        // 并交由该View的onTouchEvent()处理处个action,然后返回父控件去处理
//        return false;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() action = " + event.getAction());

        //return true;表示这里消费了本次action,不会去执行父View的onTouchEvent()
        //本次事件的其他action,也不会传递到子View,
        // 也就是说，子View可以分发第一次action，也能处理第一次action,
        // 但是后面的action，不会传递到子View去
//        return true;


        //return false和return super一样，表示不会消费本次事件，也就是说还是会执行父View的onTouchEvent()
        //如果子View没有消费本次事件，那么该View及其子View都不能分发或消费后面的事件（move和up事件）
//        return false;
        return super.onTouchEvent(event);
    }
}
