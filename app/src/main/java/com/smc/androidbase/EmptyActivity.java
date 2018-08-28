package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/8/23
 * @description
 */

public class EmptyActivity extends Activity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, EmptyActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "EmptyActivity", Toast.LENGTH_SHORT).show();
    }
}
