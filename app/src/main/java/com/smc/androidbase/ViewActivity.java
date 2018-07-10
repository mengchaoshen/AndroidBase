package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.smc.androidbase.view.CoinView;
import com.smc.androidbase.view.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/25
 * @description
 */

public class ViewActivity extends Activity {

    @BindView(R.id.iv_square)
    SquareImageView mIvSquare;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.coin_view)
    CoinView mCoinView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ViewActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        mCoinView.startAnimation();
    }
}
