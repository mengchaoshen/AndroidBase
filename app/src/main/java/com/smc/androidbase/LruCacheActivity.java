package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LruCache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/7
 * @description
 */

public class LruCacheActivity extends Activity {

    private final static String TAG = "LruCacheActivity";
    private LruCache<String, Bitmap> mLruCache;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LruCacheActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);

        int memorySize = (int) Runtime.getRuntime().totalMemory() / 1024;
        int size = memorySize / 8;

        //LruCache的作用是维护一个缓存（缓存大小可以自行设置），当缓存即将满的时候，会把没有使用的最近没有使用过的数据清理掉。
        //他的原理是用一个LinkedHashMap来存储数据，LinkedHashMap是以数组和双向链表来实现的
        mLruCache = new LruCache<String, Bitmap>(size) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return super.sizeOf(key, value);
            }
        };

//        mLruCache.put("1", null);
//        mLruCache.put("2", null);
//        Bitmap bitmap = mLruCache.get("1");

        //这里一定要设置accessOrder=true，表示存储顺序，就是每次执行get()的时候，会把key对应的数据放到链表的末尾
        //所以用在LruCache的时候，每次读取完了之后，就把该数据放在链表末尾，而缓存满的时候，会清除链表头的数据，
        // 链表头的数据，就是最近没有使用的数据
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(0, 0.75f, true);
        linkedHashMap.put("1", "1");
        linkedHashMap.put("2", "2");
        linkedHashMap.put("3", "3");
        linkedHashMap.put("4", "4");
        linkedHashMap.put("5", "5");
        linkedHashMap.put("6", "6");
        linkedHashMap.put("7", "7");
        linkedHashMap.put("8", "8");

        linkedHashMap.get("2");
        linkedHashMap.get("4");

        for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
            Log.d(TAG, "entry " + entry.getKey() + " : " + entry.getValue());
        }
    }
}
