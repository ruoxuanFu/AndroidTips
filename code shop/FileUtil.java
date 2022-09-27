package com.hc.myapplication.utils;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public static void delFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除文件夹
     *
     * @param file File
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File value : files) {
                        deleteFile(value);
                    }
                }
            }
            file.delete();
        }
    }

    /**
     * 生成文件
     *
     * @param filePath String
     * @param fileName String
     * @return File
     */
    public static File makeFilePath(String filePath, String fileName) throws IOException {
        makeRootDirectory(filePath);
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 生成文件夹
     *
     * @param filePath String
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
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

    /*
     * ---文件流读写相关
     */

    /**
     * 读取raw目录的文件内容
     *
     * @param context   内容上下文
     * @param rawFileId raw文件名id
     * @return String
     */
    public static String readRawValue(Context context, int rawFileId) {
        String result = "";
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(rawFileId);
            int len = is.available();
            byte[] buffer = new byte[len];
            is.read(buffer);
            result = new String(buffer, StandardCharsets.UTF_8);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context  内容上下文
     * @param fileName 文件名称，包含扩展名
     * @return String
     */
    public static String readAssetsValue(Context context, String fileName) {
        String result = "";
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open(fileName);
            int len = is.available();
            byte[] buffer = new byte[len];
            is.read(buffer);
            result = new String(buffer, StandardCharsets.UTF_8);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context  内容上下文
     * @param fileName 文件名称，包含扩展名
     * @return List<String>
     */
    public static List<String> readAssetsListValue(Context context, String fileName) {
        List<String> list = new ArrayList<>();
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = context.getResources().getAssets().open(fileName);
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String str;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 写入文件到私有目录下files文件夹下
     * getFilesDir()
     *
     * @param context  上下文
     * @param fileName 文件名称
     * @param content  文件内容
     */
    public static void write(Context context, String fileName, String content) {
        try {
            FileOutputStream outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outStream.write(content.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件到私有目录下files文件夹下
     * getFilesDir()
     *
     * @param context  上下文
     * @param fileName 文件名称
     * @param content  文件内容
     */
    public static void write(Context context, String fileName, byte[] content) {
        try {
            FileOutputStream outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outStream.write(content);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     *
     * @param inputStream 字节流对象
     * @param filePath    文件的存放路径(带文件名称)
     */
    public static File write(InputStream inputStream, String filePath) {
        OutputStream outputStream = null;
        // 在指定目录创建一个空文件并获取文件对象
        File mFile = new File(filePath);
        if (!mFile.getParentFile().exists()) {
            mFile.getParentFile().mkdirs();
        }
        try {
            outputStream = new FileOutputStream(mFile);
            byte[] buffer = new byte[4 * 1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mFile;
    }

    /**
     * 序列化对象到文件
     *
     * @param rsls     需要序列化的对象
     * @param filename 文件名
     */
    public static synchronized <T> void serializeObject(T rsls, String filename) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        OutputStream os = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(rsls);
            byte[] data = baos.toByteArray();
            os = new FileOutputStream(filename);
            os.write(data);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反序列化对象
     *
     * @param filename 文件名
     * @return T
     */
    @Nullable
    public static synchronized <T> T deserializeObject(String filename) {
        T obj = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(filename);
            ois = new ObjectInputStream(fis);
            while (fis.available() > 0) {
                obj = (T) ois.readObject();
            }
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /*
     * 文件流读写相关---
     */
}
