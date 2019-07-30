package com.smc.androidbase.utils;


import android.content.Context;
import android.widget.Toast;

/**
 * 提示工具类
 */
public class ToastUtil {

    private static Toast toast;

    private static Context mApplicationContext;

    public static void setApplicationContext(Context appContext) {
        mApplicationContext = appContext;
    }

    /**
     * 设置一个自定义的弱提示容器
     *
     * @param toast
     */
    public static void setToast(Toast toast) {
        ToastUtil.toast = toast;
    }

    /**
     * 提示
     *
     * @param id      显示的字符资源id
     * @param context
     */
    public static void showToast(int id, Context context) {
        showToast(id, context, Toast.LENGTH_SHORT);
    }

    /**
     * 提示
     *
     * @param id      显示的字符资源id
     * @param context
     * @param time    短||长显示
     */
    public static void showToast(int id, Context context, int time) {
        if (toast == null) {
            toast = Toast.makeText(mApplicationContext == null ? context.getApplicationContext() : mApplicationContext, context.getResources()
                    .getString(id), time);
        } else {
            toast.setText(context.getResources().getString(id));
        }
        toast.show();
    }

    /**
     * 提示
     *
     * @param msg     显示的字符
     * @param context
     */
    public static void showToast(String msg, Context context) {
        showToast(msg, context, Toast.LENGTH_SHORT);
    }

    /**
     * 提示
     *
     * @param msg     显示的字符
     * @param context
     * @param time    短||长显示
     */
    public static void showToast(String msg, Context context, int time) {
        if (toast == null) {
            toast = Toast.makeText(mApplicationContext == null ? context.getApplicationContext() : mApplicationContext, msg, time);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 吐司，使用Application的context。
     * 你首先应调用 {@link ToastUtil#setApplicationContext(Context)} 初始化
     *
     * @param msg
     */
    public static void showToast(String msg) {
        if (mApplicationContext == null) {
            throw new RuntimeException("you should call setApplicationContext first!");
        }
        toast = Toast.makeText(mApplicationContext, msg, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

}
