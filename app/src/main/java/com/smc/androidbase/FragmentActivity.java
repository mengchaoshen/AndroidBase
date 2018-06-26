package com.smc.androidbase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.smc.androidbase.fragment.MyFragment1;
import com.smc.androidbase.fragment.MyFragment2;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/5/29
 * @description
 */

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout mFrameLayout;
    private MyFragment1 mMyFragment1;
    private MyFragment2 mMyFragment2;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private TextView mTvRed, mTvBlue;

    public static void launch(Context context){
        Intent intent = new Intent(context, FragmentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FragmentActivity", "onCreate()");
        setContentView(R.layout.activity_fragment);

        mFrameLayout = findViewById(R.id.frameLayout);
        mTvRed = findViewById(R.id.tv_red);
        mTvBlue = findViewById(R.id.tv_blue);

        mTvRed.setOnClickListener(this);
        mTvBlue.setOnClickListener(this);

        //放置屏幕旋转导致不停的添加fragment
        if(null == savedInstanceState){
            mMyFragment1 = new MyFragment1();
            mMyFragment2 = new MyFragment2();
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.add(R.id.frameLayout, mMyFragment1).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("FragmentActivity", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("FragmentActivity", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("FragmentActivity", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("FragmentActivity", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("FragmentActivity", "onDestroy()");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_red:
                mFragmentTransaction = mFragmentManager.beginTransaction();
                //使用replace()方法来切换fragment，点击back回来时，view会重新绘制。
                // 也就是说，回调了onPause()onStop()onDestroyView(),但是不会回调onDestroy()
                // 因为Fragment其实还是存活的，只是里面的布局被清空了

                //使用hide()add()来切换fragment,点击back回来时，view还是保留之前的。
                //mFragmentTransaction.replace(R.id.frameLayout, mMyFragment1);
                mFragmentTransaction.hide(mMyFragment2);
                mFragmentTransaction.add(R.id.frameLayout, mMyFragment1);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            case R.id.tv_blue:
                //这里每次要进行transaction操作，都需要beginTransaction()，不然会报错。
                mFragmentTransaction = mFragmentManager.beginTransaction();
//                mFragmentTransaction.replace(R.id.frameLayout, mMyFragment2);
                mFragmentTransaction.hide(mMyFragment1);
                mFragmentTransaction.add(R.id.frameLayout, mMyFragment2);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            default :
                break;
        }
    }
}
