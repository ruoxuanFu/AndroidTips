package com.sflep.course.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Insets;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

/**
 * @author furuoxuan
 */
public class ScreenUtil {

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width;
        if (DeviceUtil.hasR()) {
            WindowMetrics windowMetrics = wm.getCurrentWindowMetrics();
            WindowInsets windowInsets = windowMetrics.getWindowInsets();
            Insets insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.statusBars() |
                    WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
            int insetsWidth = insets.left + insets.right;
            width = windowMetrics.getBounds().width() - insetsWidth;
        } else {
            width = wm.getDefaultDisplay().getWidth();
        }
        return width;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height;
        if (DeviceUtil.hasR()) {
            WindowMetrics windowMetrics = wm.getCurrentWindowMetrics();
            WindowInsets windowInsets = windowMetrics.getWindowInsets();
            Insets insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.statusBars() |
                    WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
            int insetsHeight = insets.top + insets.bottom;
            height = windowMetrics.getBounds().height() - insetsHeight;
        } else {
            height = wm.getDefaultDisplay().getHeight();
        }
        return height;
    }

    /**
     * 是否使屏幕常亮
     *
     * @param activity 当前的页面的Activity
     */
    public static void keepScreenLongLight(Activity activity, boolean isOpenLight, boolean maxBrightness) {

        Window window = activity.getWindow();
        if (isOpenLight) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        WindowManager.LayoutParams windowLayoutParams = window.getAttributes();
        windowLayoutParams.screenBrightness = maxBrightness ? WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
                : WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        window.setAttributes(windowLayoutParams);
    }

    /*
     * ---测量布局的尺寸
     */

    /**
     * 在onCreate后调用，获取视图的尺寸
     *
     * <p>需回调onGetSizeListener接口，在onGetSize中获取view宽高</p>
     * <p>用法示例如下所示</p>
     * <pre>
     * ScreenUtil.forceGetViewSize(view, new ScreenUtil.OnViewSizeCallback() {
     *     Override
     *     public void onViewSize(View view) {
     *         view.getWidth();
     *     }
     * });
     * </pre>
     *
     * @param view     视图
     * @param listener 监听器
     */
    public static void forceGetViewSize(final View view, OnViewSizeCallback listener) {
        view.post(() -> {
            if (listener != null) {
                listener.onViewSize(view);
            }
        });
    }

    /**
     * 获取到View尺寸的回调
     */
    public interface OnViewSizeCallback {

        /**
         * 获取尺寸
         *
         * @param view View
         */
        void onViewSize(View view);
    }

    /*
     * 测量布局的尺寸---
     */
}
