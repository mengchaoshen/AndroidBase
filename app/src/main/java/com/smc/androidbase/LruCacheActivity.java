package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LruCache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/7
 * @description
 */

public class LruCacheActivity extends Activity {

    public static void main(String[] args){

        //accessOrder的含义是，accessOrder=true，表示按照访问顺序遍历，get put都会改变访问顺序，get put操作之后，会把数据放到最后面
        //accessOrder=false，表示按照存储顺序，遍历顺序是按照插入顺序从先到后输出，也就是先插入的先输出，get put不会影响变量顺序
        Map<String, String> map = new LinkedHashMap<>(16,0.75f,true);
        map.put("apple", "苹果");
        map.put("watermelon", "西瓜");
        map.put("banana", "香蕉");
        map.put("peach", "桃子");
        map.put("apple", "苹果");

//        map.get("banana");
//        map.get("apple");


        for(Map.Entry<String, String> entry : map.entrySet()){
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
//        Iterator iter = map.entrySet().iterator();
//        while (iter.hasNext()) {
//            Map.Entry entry = (Map.Entry) iter.next();
//            System.out.println(entry.getKey() + "=" + entry.getValue());
//        }

    }

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
