package com.smc.androidbase;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/5/29
 * @description
 */

public class NotificationShowActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
    }
}
