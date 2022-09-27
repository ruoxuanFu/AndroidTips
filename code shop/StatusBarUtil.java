package com.hc.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.lang.reflect.Field;

/**
 * 状态栏透明,状态栏黑色文字，状态栏颜色，沉浸式状态栏
 *
 * @author furuoxuan
 */
public class StatusBarUtil {

    public static int DEFAULT_COLOR = 0;
    public static float DEFAULT_ALPHA = 0;

    /**
     * 设置状态栏背景颜色
     */
    public static void setColor(Activity activity, @ColorInt int color) {
        if (DeviceUtil.hasLollipop()) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(color);
        } else if (DeviceUtil.hasKitkat()) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup systemContent = activity.findViewById(android.R.id.content);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            systemContent.getChildAt(0).setFitsSystemWindows(true);
            systemContent.addView(statusBarView, 0, lp);
        }
    }

    /*
     * ---沉浸式状态栏
     */

    public static void immersive(Activity activity) {
        immersive(activity, DEFAULT_COLOR, DEFAULT_ALPHA);
    }

    public static void immersive(Activity activity, int color, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        immersive(activity.getWindow(), color, alpha);
    }

    public static void immersive(Activity activity, int color) {
        immersive(activity.getWindow(), color, 1f);
    }

    public static void immersive(Window window) {
        immersive(window, DEFAULT_COLOR, DEFAULT_ALPHA);
    }

    public static void immersive(Window window, int color) {
        immersive(window, color, 1f);
    }

    public static void immersive(Window window, int color, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        if (DeviceUtil.hasLollipop()) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mixtureColor(color, alpha));

            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
        } else if (DeviceUtil.hasKitkat()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setTranslucentView((ViewGroup) window.getDecorView(), color, alpha);
        } else if (DeviceUtil.hasJellyBean()) {
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }

    /**
     * 创建假的透明状态栏实现沉浸式状态栏
     *
     * @param container ViewGroup
     * @param color     int
     * @param alpha     float
     */
    public static void setTranslucentView(ViewGroup container, int color,
            @FloatRange(from = 0.0, to = 1.0) float alpha) {
        if (DeviceUtil.hasKitkat()) {
            int mixtureColor = mixtureColor(color, alpha);
            View translucentView = container.findViewById(android.R.id.custom);
            if (translucentView == null && mixtureColor != 0) {
                translucentView = new View(container.getContext());
                translucentView.setId(android.R.id.custom);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getStatusBarHeight(container.getContext()));
                container.addView(translucentView, lp);
            }
            if (translucentView != null) {
                translucentView.setBackgroundColor(mixtureColor);
            }
        }
    }

    /*
     * 沉浸式状态栏---
     */

    /**
     * 获取透明度颜色
     *
     * @param color int
     * @param alpha float
     * @return int
     */
    public static int mixtureColor(int color, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        int a = (color & 0xff000000) == 0 ? 0xff : color >>> 24;
        return (color & 0x00ffffff) | (((int) (a * alpha)) << 24);
    }

    /**
     * 设置状态栏字体图标颜色
     *
     * @param activity Activity
     * @param dark     boolean, true为黑色字体, false为白色字体
     */
    public static void setStatusBarTextStyle(Activity activity, boolean dark) {
        Window window = activity.getWindow();
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
        controller.setAppearanceLightStatusBars(dark);
    }

    /*
     * ---获取状态栏的高度。
     */

    /**
     * 在某些机子上存在不同的density值，所以增加两个虚拟值
     */
    private static int sStatusBarHeight = -1;

    private static float sVirtualDensity = -1;

    /**
     * 大部分状态栏都是25dp
     */
    private final static int STATUS_BAR_DEFAULT_HEIGHT_DP = 25;

    public static int getStatusBarHeight(Context context) {
        if (sStatusBarHeight == -1) {
            initStatusBarHeight(context);
        }
        return sStatusBarHeight;
    }

    @SuppressLint("PrivateApi")
    private static void initStatusBarHeight(Context context) {
        Class<?> clazz;
        Object obj = null;
        Field field = null;
        try {
            clazz = Class.forName("com.android.internal.R$dimen");
            obj = clazz.newInstance();
            if (DeviceUtil.isMeizu()) {
                try {
                    field = clazz.getField("status_bar_height_large");
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            if (field == null) {
                field = clazz.getField("status_bar_height");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (field != null && obj != null) {
            try {
                int id = Integer.parseInt(field.get(obj).toString());
                sStatusBarHeight = context.getResources().getDimensionPixelSize(id);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        if (DeviceUtil.isTablet(context) && sStatusBarHeight > CommUtil.dip2px(STATUS_BAR_DEFAULT_HEIGHT_DP)) {
            //状态栏高度大于25dp的平板，状态栏通常在下方
            sStatusBarHeight = 0;
        } else {
            if (sStatusBarHeight <= 0) {
                if (sVirtualDensity == -1) {
                    sStatusBarHeight = CommUtil.dip2px(STATUS_BAR_DEFAULT_HEIGHT_DP);
                } else {
                    sStatusBarHeight = (int) (STATUS_BAR_DEFAULT_HEIGHT_DP * sVirtualDensity + 0.5f);
                }
            }
        }
    }

    /*
     * 获取状态栏的高度---
     */

    /*
     * ---适配状态栏高度
     */

    /**
     * 通过对view设置Padding适配状态栏高度
     *
     * @param view View
     */
    public static void fitsStatusBarViewPadding(View view) {
        //增加高度
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height += getStatusBarHeight(view.getContext());

        //设置PaddingTop
        view.setPadding(view.getPaddingLeft(),
                view.getPaddingTop() + getStatusBarHeight(view.getContext()),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    private static final String FITS_STATUS_BAR_VIEW_MARGIN_TAG = "fitStatusBarMargin";
    private static final String FITS_STATUS_BAR_VIEW_WRAP_TAG = "fitStatusBarWrap";

    /**
     * 通过对view设置Margin适配状态栏高度
     *
     * @param view View
     */
    public static void fitsStatusBarViewMargin(View view) {

        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {

            //通过tag判断是否添加过
            if (view.getTag() != null && view.getTag().equals(FITS_STATUS_BAR_VIEW_MARGIN_TAG)) {
                return;
            }

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            int marginTop = layoutParams.topMargin;
            int setMarginTop = marginTop + getStatusBarHeight(view.getContext());
            view.setTag(FITS_STATUS_BAR_VIEW_MARGIN_TAG);
            layoutParams.topMargin = setMarginTop;
            view.requestLayout();
        }
    }

    /**
     * 通过布局包裹来适配状态栏高度
     *
     * @param view View
     */
    public static void fitsStatusBarViewWrap(View view) {
        ViewParent fitParent = view.getParent();
        if (fitParent != null) {

            //通过tag判断是否添加过
            if (((fitParent instanceof LinearLayout) &&
                    ((ViewGroup) fitParent).getTag() != null &&
                    ((ViewGroup) fitParent).getTag().equals(FITS_STATUS_BAR_VIEW_WRAP_TAG))) {
                return;
            }

            //给当前布局包装一个适应布局
            ViewGroup fitGroup = (ViewGroup) fitParent;
            fitGroup.removeView(view);

            LinearLayout fitLayout = new LinearLayout(view.getContext());
            fitLayout.setOrientation(LinearLayout.VERTICAL);
            fitLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            fitLayout.setTag(FITS_STATUS_BAR_VIEW_WRAP_TAG);

            //先加一个状态栏高度的布局
            View statusView = new View(view.getContext());
            statusView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(view.getContext())));
            fitLayout.addView(statusView);

            ViewGroup.LayoutParams fitViewParams = view.getLayoutParams();

            fitLayout.addView(view);
            fitGroup.addView(fitLayout);
        }
    }

    /*
     * 适配状态栏高度---
     */

    /**
     * 全屏展示
     * View.SYSTEM_UI_FLAG_LAYOUT_STABLE：全屏显示时保证尺寸不变。
     * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，状态栏显示在Activity页面上面。
     * View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
     * View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏导航栏
     * View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。
     * View.SYSTEM_UI_FLAG_VISIBLE：Activity非全屏显示，显示状态栏和导航栏。
     * View.INVISIBLE：Activity伸展全屏显示，隐藏状态栏。
     * View.SYSTEM_UI_LAYOUT_FLAGS：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
     * View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY：必须配合 View.SYSTEM_UI_FLAG_FULLSCREEN 和 View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
     * 组合使用，达到的效果是拉出状态栏和导航栏后显示一会儿消失。
     *
     * 设置全屏theme
     * <style name="Theme_FullScreen" parent="Theme.AppCompat.Light.NoActionBar">
     * <item name="android:windowFullscreen">true</item>
     * <item name="windowNoTitle">true</item>
     * <!--设置顶部状态栏是否为透明-->
     * <item name="android:windowTranslucentStatus">false</item>
     * <!--Android 5.x开始需要把颜色设置透明，否则顶部导航栏会呈现系统默认的浅灰色-->
     * <item name="android:statusBarColor">@android:color/transparent</item>
     * <!--设置顶部状态栏和底部导航栏是否为透明-->
     * <item name="android:windowTranslucentNavigation">true</item>
     * <item name="windowActionBar">false</item>
     * <item name="android:windowLayoutInDisplayCutoutMode">shortEdges</item>
     * </style>
     * 如果有的水滴屏幕可能会在状态栏的位置出现黑条
     * 解决办法1：
     * 在目标Activity中：
     * if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
     * WindowManager.LayoutParams lp = getWindow().getAttributes();
     * lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
     * getWindow().setAttributes(lp);
     * } else {
     * getWindow().requestFeature(Window.FEATURE_NO_TITLE);
     * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
     * }
     * 解决办法2：theme中添加这条属性
     * <item name="android:windowLayoutInDisplayCutoutMode">shortEdges</item>
     */
    public void fullScreen(Window window) {
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
