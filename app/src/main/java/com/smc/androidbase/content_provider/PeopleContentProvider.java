package com.smc.androidbase.content_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/5/30
 * @description
 */

public class PeopleContentProvider extends ContentProvider {

    private static final String DB_NAME="people.db";
    private static final String DB_TABLE="peopleinfo";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase db;
    private DBOpenHelper dbOpenHelper;

    private static final int MULTIPLE_PEOPLE = 1;//访问表的所有列
    private static final int SINGLE_PEOPLE = 2;//访问单独的列
    private static final UriMatcher uriMatcher;

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(People.AUTHORITY, People.PATH_MULTIPLE, MULTIPLE_PEOPLE);
        uriMatcher.addURI(People.AUTHORITY, People.PATH_SINGLE, SINGLE_PEOPLE);
    }

    @Override
    public boolean onCreate() {
        Log.d("PeopleContentProvider", "onCreate()");
        Context context = getContext();
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        db = dbOpenHelper.getWritableDatabase();
        if(db == null){
            return false;
        }else{
            return true;
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d("PeopleContentProvider", "query()");
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();//最API
        qb.setTables(DB_TABLE);
        switch (uriMatcher.match(uri)){
            case SINGLE_PEOPLE:
                qb.appendWhere(People.KEY_ID+"="+uri.getPathSegments().get(1));
                break;
            default:
                break;
        }
        Cursor cursor = qb.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case MULTIPLE_PEOPLE://多條數據的處理
                return People.MIME_TYPE_MULTIPLE;
            case SINGLE_PEOPLE://單條數據的處理
                return People.MIME_TYPE_SINGLE;
            default:
                throw new IllegalArgumentException("Unkown uro:"+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d("PeopleContentProvider", "insert()");
        //如果添加成功，利用新添加的Id
        long id =db.insert(DB_TABLE, null, values);
        if(id>0){

            //content://contacts/people/45 这个URI就可以写成如下形式：
            // Uri person = ContentUris.withAppendedId(People.CONTENT_URI,  45);

            Uri newUri = ContentUris.withAppendedId(People.CONTENT_URI, id);
            //通知监听器，数据已经改变
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case MULTIPLE_PEOPLE:
                count = db.delete(DB_TABLE, selection, selectionArgs);
                break;
            case SINGLE_PEOPLE:
                String segment = uri.getPathSegments().get(1);
                count = db.delete(DB_TABLE, People.KEY_ID + "=" + segment, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)){
            case MULTIPLE_PEOPLE:
                count = db.update(DB_TABLE, values, selection, selectionArgs);
                break;
            case SINGLE_PEOPLE:
                String segment = uri.getPathSegments().get(1);
                count = db.update(DB_TABLE, values, People.KEY_ID + "=" + segment, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private static class DBOpenHelper extends SQLiteOpenHelper {

        private static final  String DB_CREATE = "create table "+
                DB_TABLE+"("+People.KEY_ID+" integer primary key autoincrement, "+
                People.KEY_NAME+" text not null, "+People.KEY_AGE+" integer, "+
                People.KEY_HEIGHT+" float);";

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DB_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);

        }
    }
}
