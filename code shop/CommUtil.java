package com.hc.myapplication.utils;

import android.app.ActivityManager;
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
 * <p>
 * CommUtil.init(this, new MyHandler(Looper.getMainLooper()), Looper.getMainLooper().getThread().getId());
 * static class MyHandler extends Handler {
 *
 * public MyHandler(@NonNull Looper looper) {
 * super(looper);
 * }
 *
 * public void handleMessage(@NonNull Message msg) {
 * super.handleMessage(msg);
 * }
 * }
 * </p>
 *
 * @author furuoxuan
 */
public class CommUtil {

    private static Application mApplication;
    private static Handler mHandler;
    private static long mMainThreadId;

    public static void init(Application application, Handler handler, long mainThreadId) {
        mApplication = application;
        mHandler = handler;
        mMainThreadId = mainThreadId;
    }

    public static Application getApplication() {
        return mApplication;
    }

    public static Context getContext() {
        return mApplication.getApplicationContext();
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static long getMainThreadId() {
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

    /**
     * 根据开始颜色和结束颜色计算出百分比的当前颜色值
     */
    public static int evaluate(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }

    /**
     * 获取当前进程名
     */
    private static String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) mApplication
                .getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
                if (process.pid == pid) {
                    processName = process.processName;
                }
            }
        }
        return processName;
    }

    /**
     * 判断当前进程是否为主进程
     */
    public static boolean isMainProcess(Context sContext) {
        return sContext.getApplicationContext().getPackageName().equals(getCurrentProcessName());
    }
}
