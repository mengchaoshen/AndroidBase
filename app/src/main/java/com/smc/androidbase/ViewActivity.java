package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/25
 * @description
 */

public class ViewActivity extends Activity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ViewActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }
}
