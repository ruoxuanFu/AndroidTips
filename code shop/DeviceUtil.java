package com.sflep.course.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设备帮助类
 */
@SuppressLint("PrivateApi")
public class DeviceUtil {
    private final static String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_FLYME_VERSION_NAME = "ro.build.display.id";
    private final static String FLYME = "flyme";
    private final static String ZTEC2016 = "zte c2016";
    private final static String ZUKZ1 = "zuk z1";
    private final static String ESSENTIAL = "essential";
    private final static String[] MEIZUBOARD = {"m9", "M9", "mx", "MX"};

    @Nullable
    private static String sMiuiVersionName;
    @Nullable
    private static String sFlymeVersionName;

    private static boolean sIsTabletChecked = false;
    private static boolean sIsTabletValue = false;
    private static final String BRAND = Build.BRAND.toLowerCase();

    static {
        Properties properties = new Properties();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // android 8.0，读取 /system/uild.prop 会报 permission denied
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                properties.load(fileInputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Class<?> clzSystemProperties;
        try {
            clzSystemProperties = Class.forName("android.os.SystemProperties");
            Method getMethod = clzSystemProperties.getDeclaredMethod("get", String.class);
            // miui
            sMiuiVersionName = getLowerCaseName(properties, getMethod, KEY_MIUI_VERSION_NAME);
            //flyme
            sFlymeVersionName = getLowerCaseName(properties, getMethod, KEY_FLYME_VERSION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断是否为平板设备
     */
    public static boolean isTablet(Context context) {
        if (sIsTabletChecked) {
            return sIsTabletValue;
        }
        sIsTabletValue = isTabletDevice(context);
        sIsTabletChecked = true;
        return sIsTabletValue;
    }

    /**
     * 判断是否是flyme系统
     */
    public static boolean isFlyme() {
        return !TextUtils.isEmpty(sFlymeVersionName) && sFlymeVersionName.contains(FLYME);
    }

    /**
     * 判断是否是MIUI系统
     */
    public static boolean isMIUI() {
        return !TextUtils.isEmpty(sMiuiVersionName);
    }

    public static boolean isMIUIV5() {
        return "v5".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV6() {
        return "v6".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV7() {
        return "v7".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV8() {
        return "v8".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV9() {
        return "v9".equals(sMiuiVersionName);
    }

    public static boolean isFlymeLowerThan(int majorVersion) {
        return isFlymeLowerThan(majorVersion, 0, 0);
    }

    public static boolean isFlymeLowerThan(int majorVersion, int minorVersion, int patchVersion) {
        boolean isLower = false;
        if (sFlymeVersionName != null && !sFlymeVersionName.equals("")) {
            try {
                Pattern pattern = Pattern.compile("(\\d+\\.){2}\\d");
                Matcher matcher = pattern.matcher(sFlymeVersionName);
                if (matcher.find()) {
                    String versionString = matcher.group();
                    if (versionString.length() > 0) {
                        String[] version = versionString.split("\\.");
                        if (version.length >= 1) {
                            if (Integer.parseInt(version[0]) < majorVersion) {
                                isLower = true;
                            }
                        }

                        if (version.length >= 2 && minorVersion > 0) {
                            if (Integer.parseInt(version[1]) < majorVersion) {
                                isLower = true;
                            }
                        }

                        if (version.length >= 3 && patchVersion > 0) {
                            if (Integer.parseInt(version[2]) < majorVersion) {
                                isLower = true;
                            }
                        }
                    }
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return isMeizu() && isLower;
    }


    public static boolean isMeizu() {
        return isPhone(MEIZUBOARD) || isFlyme();
    }

    /**
     * 判断是否为小米
     * https://dev.mi.com/doc/?p=254
     */
    public static boolean isXiaomi() {
        return Build.MANUFACTURER.toLowerCase().equals("xiaomi");
    }

    public static boolean isVivo() {
        return BRAND.contains("vivo") || BRAND.contains("bbk");
    }

    public static boolean isOppo() {
        return BRAND.contains("oppo");
    }

    public static boolean isHuawei() {
        return BRAND.contains("huawei") || BRAND.contains("honor");
    }

    public static boolean isEssentialPhone() {
        return BRAND.contains("essential");
    }


    /**
     * 判断是否为 ZUK Z1 和 ZTK C2016。
     * 两台设备的系统虽然为 android 6.0，但不支持状态栏icon颜色改变，因此经常需要对它们进行额外判断。
     */
    public static boolean isZUKZ1() {
        final String board = Build.MODEL;
        return board != null && board.toLowerCase().contains(ZUKZ1);
    }

    public static boolean isZTKC2016() {
        final String board = Build.MODEL;
        return board != null && board.toLowerCase().contains(ZTEC2016);
    }

    private static boolean isPhone(String[] boards) {
        final String board = Build.BOARD;
        if (board == null) {
            return false;
        }
        for (String board1 : boards) {
            if (board.equals(board1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断悬浮窗权限（目前主要用户魅族与小米的检测）。
     */
    public static boolean isFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            //24 是AppOpsManager.OP_SYSTEM_ALERT_WINDOW 的值，该值无法直接访问
            return checkOp(context, 24);
        } else {
            try {
                return (context.getApplicationInfo().flags & 1 << 27) == 1 << 27;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @TargetApi(19)
    private static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int property = (Integer) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
                return AppOpsManager.MODE_ALLOWED == property;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Nullable
    private static String getLowerCaseName(Properties p, Method get, String key) {
        String name = p.getProperty(key);
        if (TextUtils.isEmpty(name)) {
            try {
                name = (String) get.invoke(null, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(name)) {
            name = name.toLowerCase();
        }
        return name;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return Md5Util.md5(id);
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        }
    }


    public static void killProcess(String killName) {
        // 获取一个ActivityManager 对象
        ActivityManager activityManager =
                (ActivityManager) CommUtil.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        // 获取系统中所有正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager
                .getRunningAppProcesses();
        // 对系统中所有正在运行的进程进行迭代，如果进程名所要杀死的进程，则Kill掉
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            String processName = appProcessInfo.processName;
            if (processName.equals(killName)) {
                killProcessByPid(appProcessInfo.pid);
            }
        }
    }

    /**
     * 根据要杀死的进程id执行Shell命令已达到杀死特定进程的效果
     *
     * @param pid
     */
    private static void killProcessByPid(int pid) {
        String command = "kill -9 " + pid + "\n";
        Runtime runtime = Runtime.getRuntime();
        Process proc;
        try {
            proc = runtime.exec(command);
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println(e);
        }

    }


    /**
     * sdk >= 16
     *
     * @return boolean
     */
    @SuppressLint("ObsoleteSdkInt")
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * sdk >= 19
     *
     * @return boolean
     */
    @SuppressLint("ObsoleteSdkInt")
    public static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * sdk >= 21
     *
     * @return boolean
     */
    @SuppressLint("ObsoleteSdkInt")
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * sdk >= 30
     *
     * @return boolean
     */
    @SuppressLint("ObsoleteSdkInt")
    public static boolean hasR() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

}
