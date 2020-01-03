package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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
        setContentView(R.layout.activity_test);
        Toast.makeText(this, "EmptyActivity", Toast.LENGTH_SHORT).show();
//        View v1 = findViewById(R.id.v_1);
//        v1.setZ(3);
//        View v2 = findViewById(R.id.v_2);
//        v2.setZ(2);
//        View v3 = findViewById(R.id.v_3);
//        v3.setZ(1);
    }
}
