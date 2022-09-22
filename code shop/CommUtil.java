package com.sflep.course.util;

import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * 直接在application中初始化
 *
 * @author furuoxuan
 */
public class CommUtil {

    private static Application mApplication;
    private static Handler mHandler;
    private static int mMainThreadId;

    public static void init(Application application, Handler handler, int mainThreadId) {
        mApplication = application;
        mHandler = handler;
        mMainThreadId = mainThreadId;
    }

    public static Context getContext() {
        return mApplication.getApplicationContext();
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /*
     * ---获取资源
     */

    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    public static int[] getIntArray(int id) {
        return getContext().getResources().getIntArray(id);
    }

    @Nullable
    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    public static int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    @Nullable
    public static ColorStateList getColorStateList(int id) {
        return ContextCompat.getColorStateList(getContext(), id);
    }

    public static int getDimens(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    /*
     * 获取资源---
     */

    /*
     * 屏幕尺寸转换---
     */

    public static int dip2px(int dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    public static float dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (dip * density + 0.5f);
    }

    public static float px2dip(float px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    /*
     * ---屏幕尺寸转换
     */

    /**
     * 加载布局文件
     *
     * @param id 布局id
     * @return View
     */
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    /**
     * 是否运行在主线程
     *
     * @return boolean
     */
    public static boolean isRunOnUiThread() {
        int myTid = android.os.Process.myTid();
        return myTid == getMainThreadId();
    }

    /**
     * 切换到主线程运行
     *
     * @param runnable Runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isRunOnUiThread()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

}
