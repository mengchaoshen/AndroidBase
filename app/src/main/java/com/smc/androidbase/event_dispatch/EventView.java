package com.smc.androidbase.event_dispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/6
 * @description
 */

public class EventView extends View {

    private final static String TAG = "EventView";

    public EventView(Context context) {
        super(context);
    }

    public EventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent() action = " + event.getAction());

        //return true表示不分发本次action，也表示消费了本次action，不会调用该View的onTouchEvent也不会调用父View的onTouchEvent
        //本次事件的其他action，还是可以按照初始状态继续分发和消费
//        return true;

        //return false表示不分发本次action，也不消费本次action，该View不会执行onTouchEvent,但是会把action交给父View去处理
        //本次事件的其他action，还是可以按照初始状态继续分发和消费
//        return false;

        //return super表示把本次action分发给自己，该View的onTouchEvent会被执行，
        //父View的onTouchEvent是否会被执行，由该View的onTouchEvent的返回值所决定
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() action = " + event.getAction());

        //return true表示该View消费了本次action，父View的onTouchEvent将不会执行。
        //本次事件的其他action,还是按照初始的状态进行分发和消费
        return true;

        //return false和return super一样表示该View不消费本次action,父View还是会执行onTouchEvent
        //本次事件的其他action,都不会分发到该View
//        return false;
//        return super.onTouchEvent(event);
    }
}
