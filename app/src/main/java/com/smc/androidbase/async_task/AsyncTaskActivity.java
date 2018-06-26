package com.smc.androidbase.async_task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/5
 * @description
 */

public class AsyncTaskActivity extends Activity {

    private AsyncTask<String, Integer, String> mAsyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //由于AsyncTask里面的onPreExecute，onProgressUpdate，onPostExecute这些生命周期需要在主线程中调用
        //所以，调用构造函数必须在主线程中调用
        mAsyncTask = new AsyncTask<String, Integer, String>() {

            //这个回调方法，是在丢入线程池之前调用的，是在主线程中执行的
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //这个回调是在WorkRunnable里面调用的，也就是在子线程中执行的
            @Override
            protected String doInBackground(String... strings) {

                publishProgress(1);

                cancel(true);
                return null;
            }

            //在执行了publishProgress()方法后，AsyncTask内部会用InternalHandler去调用onProgressUpdate()方法
            //InternalHandler是在主线程中创建的，所以onProgressUpdate()也是在主线中
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            //在WorkRunnable中的call()方法执行完后，会通过InternalHandler去执行onPostExecute()方法，
            // 所以onPostExecute()回调也是在主线程中
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            //在WorkRunnable中的call()方法执行完后，如果状态被置为了cancel，
            // 那这个时候不会执行onPostExecute()，而是去执行了onCancelled()
            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        };

        //execute()方法，执行是把这任务添加到SERIAL_EXECUTOR中去，然后队列执行
        mAsyncTask.execute("");
        //如果想要立即执行任务，则需要调用executeOnExecutor()方法，
        // 执行executeOnExecutor()会立即调用THREAD_POOL_EXECUTOR的execute()
        mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
    }
}
