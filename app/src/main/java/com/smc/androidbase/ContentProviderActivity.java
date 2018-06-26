package com.smc.androidbase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smc.androidbase.content_provider.People;
import com.smc.androidbase.content_provider.PeopleHelper;
import com.smc.androidbase.service.BackService;
import com.smc.androidbase.service.BackService2;

public class ContentProviderActivity extends AppCompatActivity {

    TextView mTvAdd, mTvQuery, mTvQuery2, mTvForeService;

    private PeopleHelper mPeopleHelper;

    public static void launch(Context context){
        Intent intent = new Intent(context, ContentProviderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        mPeopleHelper = new PeopleHelper(this);
        mTvAdd = findViewById(R.id.tv_add);
        mTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPeopleHelper.add("tom", 21, 170);
                mPeopleHelper.add("cat", 22, 167);
            }
        });
        mTvQuery = findViewById(R.id.tv_query);
        mTvQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mPeopleHelper.query();
                Toast.makeText(ContentProviderActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        mTvQuery2 = findViewById(R.id.tv_query2);
        mTvQuery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mPeopleHelper.query(2);
                Toast.makeText(ContentProviderActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
