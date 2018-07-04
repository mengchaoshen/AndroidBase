package com.smc.androidbase.bitmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.smc.androidbase.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/4
 * @description
 */

public class BitmapActivity extends Activity {

    @BindView(R.id.iv_file)
    ImageView mIvFile;
    @BindView(R.id.iv_source)
    ImageView mIvSource;
    @BindView(R.id.iv_stream)
    ImageView mIvStream;
    @BindView(R.id.iv_array)
    ImageView mIvArray;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, BitmapActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ButterKnife.bind(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize这个属性是用来压缩图片，也就是重采样，如果inSampleSize=2，也就是按照1/2的宽高重采样
        options.inSampleSize = 2;
        //options.inJustDecodeBounds = true表示只测量宽高，并不会真正把图片加载到内存中去，可以用这个测量出宽高，
        // 然后再计算采样率，把图片加载到合适的ImageView里面去，这样不会是图片模糊，也不会图片过大，浪费内存
        options.inJustDecodeBounds = true;
        Bitmap bitmapFile = BitmapFactory.decodeFile("/sdcard/arcpush2.jpg", options);
        mIvFile.setImageBitmap(bitmapFile);

        Bitmap bitmapRes = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        mIvSource.setImageBitmap(bitmapRes);


    }
}
