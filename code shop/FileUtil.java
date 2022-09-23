package com.sflep.course.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author furuoxuan
 */
public class FileUtil {

    /*
     * ---缓存相关
     */

    /**
     * 获取总共的缓存大小
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return readableFileSize(cacheSize);
    }


    /**
     * 清除所有的缓存数据
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
        //清除cache的缓存
        CacheUtil.get(context).clear();
    }

    /*
     * 缓存相关---
     */

    /**
     * 清除文件
     */
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null && children.length > 0) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            } else {
                return true;
            }
            return dir.delete();
        } else {
            return true;
        }
    }

    /**
     * 获取文件
     * Context.getExternalFilesDir() --> SDCard/Android/data/应用包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/应用包名/cache/目录，一般存放临时缓存数据
     *
     * @param file File
     * @return long
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        File[] fileList = file.listFiles();
        if (fileList != null && fileList.length > 0) {
            for (File value : fileList) {
                // 如果下面还有文件
                if (value.isDirectory()) {
                    size = size + getFolderSize(value);
                } else {
                    size = size + value.length();
                }
            }
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size（文件大小，单位为B）
     * @return String
     */
    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,###.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 文件大小智能转换
     * 会将文件大小转换为当前单位到最大满足单位（满足GB: 包含B,KB,MB,GB）
     *
     * @param size（文件大小，单位为B）
     * @return Map<String, BigDecimal>
     */
    public static Map<String, BigDecimal> readableFileSizeMap(long size) {
        Map<String, BigDecimal> map = new HashMap<>();
        if (size <= 0) {
            return map;
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        for (int i = 0; i <= digitGroups; i++) {
            map.put(units[i], new BigDecimal(size / Math.pow(1024, i)));
        }
        return map;
    }

}
