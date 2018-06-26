package com.smc.androidbase.event_dispatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.smc.androidbase.R;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/5
 * @description
 */

public class EventActivity extends Activity implements View.OnClickListener {

    private final static String TAG = "EventActivity";

    //    private ImageView mIvBg;
    private EventView mEventView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, EventActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mEventView = findViewById(R.id.event_view);
        //如果添加了OnTouchListener，就不会执行View里面的OnTouchEvent()方法
        //onTouch这里return true的话，效果跟OnTouchEvent() return true一样,然后onClick就不会被执行了。
//        mEventView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG, "onTouch() action = " + event.getAction());
//                return true;
//            }
//        });
        mEventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick()");
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent() action = " + ev.getAction());
        //return false; Activity中的dispatchTouchEvent()方法，return true/false，都表示消费结束了，
        // 也不会调用Activity的OnTouchEvent(),因为onTouchEvent()方法是在super.dispatchTouchEvent()里面调用的
        //子View中，不管哪个dispatchTouchEvent()return true，都表示分发结束，所有View的onTouchEvent()都不会去执行
        //但是本次事件的其他action还是会以初始情况继续分发
        //return true;

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() action = " + event.getAction());
        //如果子View都没有消费action，也就是onTouchEvent()没有return true的，
        // 那么子View只能分发和处理一次down事件，move和up事件都那不会分发到子View去了。
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {

    }
}
