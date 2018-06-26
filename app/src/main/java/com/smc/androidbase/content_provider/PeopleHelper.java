package com.smc.androidbase.content_provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import javax.xml.transform.URIResolver;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/5/30
 * @description
 */

public class PeopleHelper {

    ContentResolver resolver;

    public PeopleHelper(Context context){
        resolver = context.getContentResolver();
    }

    /**
     * 只需要People.CONTENT_URI
     * @param name
     * @param age
     * @param height
     */
    public void add(String name,int age,float height){
        ContentValues values = new ContentValues();
        values.put(People.KEY_NAME, name);
        values.put(People.KEY_AGE, age);
        values.put(People.KEY_HEIGHT, height);
        Uri newUri = resolver.insert(People.CONTENT_URI, values);
        Log.d("PeopleHelper", " add 添加成功，URI:" + newUri);
//        tv_show.setText("添加成功，URI:" + newUri);
    }

    /**
     * 同樣只需要URL
     */
    public void delete(){
        resolver.delete(People.CONTENT_URI, null, null);
        String msg = "数据全部删除";
        Log.d("PeopleHelper", "delete " + msg);
//        tv_show.setText(msg);
    }


    public void update(String name,int age,float height,String id){
        ContentValues values = new ContentValues();
        values.put(People.KEY_NAME, name);
        values.put(People.KEY_AGE, age);
        values.put(People.KEY_HEIGHT, height);
        Uri uri = Uri.parse(People.CONTENT_URI_STRING + "/" + id);
        int result = resolver.update(uri, values, null, null);
        String msg = "更新ID为" + id + "的数据" + (result > 0 ? "成功" : "失败");
//        tv_show.setText(msg);
        Log.d("PeopleHelper", "update " + msg);
    }

    public String query(int id){
        Uri uri = ContentUris.withAppendedId(People.CONTENT_URI, id);

        long l = ContentUris.parseId(uri);
        Log.d("PeopleHelper", "query id:" + l);
        Cursor cursor = resolver.query(uri,
                new String[]{People.KEY_ID, People.KEY_NAME, People.KEY_AGE, People.KEY_HEIGHT},
                null, null, null);
        if (cursor == null) {
//            tv_show.setText("数据库中没有数据");
            Log.d("PeopleHelper", "query 数据库中没有数据");
            return "";
        }
//        tv_show.setText("数据库：" + String.valueOf(cursor.getCount()) + "条记录");
        Log.d("PeopleHelper", "query 数据库：" + String.valueOf(cursor.getCount()) + "条记录");
        String msg= "";
        if (cursor.moveToFirst()) {
            do {
                msg += "ID: " + cursor.getString(cursor.getColumnIndex(People.KEY_ID)) + ",";
                msg += "姓名: " + cursor.getString(cursor.getColumnIndex(People.KEY_NAME)) + ",";
                msg += "年龄: " + cursor.getInt(cursor.getColumnIndex(People.KEY_AGE)) + ",";
                msg += "身高: " + cursor.getFloat(cursor.getColumnIndex(People.KEY_HEIGHT)) + ",";
            } while (cursor.moveToNext());
        }
//        tv_display.setText(msg);
        Log.d("PeopleHelper", "query " + msg);
        return msg;
    }

    public String query(){
        Cursor cursor = resolver.query(People.CONTENT_URI,
                new String[]{People.KEY_ID, People.KEY_NAME, People.KEY_AGE, People.KEY_HEIGHT},
                null, null, null);
        if (cursor == null) {
//            tv_show.setText("数据库中没有数据");
            Log.d("PeopleHelper", "query 数据库中没有数据");
            return "";
        }
//        tv_show.setText("数据库：" + String.valueOf(cursor.getCount()) + "条记录");
        Log.d("PeopleHelper", "query 数据库：" + String.valueOf(cursor.getCount()) + "条记录");
        String msg= "";
        if (cursor.moveToFirst()) {
            do {
                msg += "ID: " + cursor.getString(cursor.getColumnIndex(People.KEY_ID)) + ",";
                msg += "姓名: " + cursor.getString(cursor.getColumnIndex(People.KEY_NAME)) + ",";
                msg += "年龄: " + cursor.getInt(cursor.getColumnIndex(People.KEY_AGE)) + ",";
                msg += "身高: " + cursor.getFloat(cursor.getColumnIndex(People.KEY_HEIGHT)) + ",";
            } while (cursor.moveToNext());
        }
//        tv_display.setText(msg);
        Log.d("PeopleHelper", "query " + msg);
        return msg;
    }

}
