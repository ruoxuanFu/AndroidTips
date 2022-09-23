package com.hc.myapplication.utils;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import androidx.annotation.Nullable;

import com.getkeepsafe.relinker.ReLinker;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

/**
 * https://github.com/Tencent/MMKV
 *
 * @author furuoxuan
 */
public class MMkvUtils {

    private static volatile MMkvUtils instance;
    private final Context context;

    /**
     * 用来维护所有的sp文件转换为mmkv的状态
     */
    private static final String SP_FILE_LIST_NAMES = "spFileListNames";

    private MMkvUtils(Context context) {
        this.context = context.getApplicationContext();
    }

    public static MMkvUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (MMkvUtils.class) {
                if (instance == null) {
                    instance = new MMkvUtils(context);
                }
            }
        }
        return instance;
    }

    public MMkvUtils initMMkv(String appName) {
        String dir = context.getFilesDir().getAbsolutePath() + "/mmkv_" + context.getPackageName();
        String rootDir = MMKV.initialize(context,
                dir,
                libName -> {
                    // 解决一些低版本的Android设备因为libmmkv.so找不到，报错java.lang.UnsatisfiedLinkError之类的crash
                    // https://github.com/KeepSafe/ReLinker
                    ReLinker.loadLibrary(context, libName);
                },
                MMKVLogLevel.LevelInfo);
        return this;
    }

    /**
     * 转移旧版本sp中的数据到 mmkv中
     *
     * @param spName String
     */
    public MMKV importSpValue(String spName) {
        boolean hasImport = MMKV.mmkvWithID(SP_FILE_LIST_NAMES).getBoolean(spName, false);
        if (!hasImport) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(spName, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            MMKV mmkv = MMKV.mmkvWithID(spName);
            mmkv.importFromSharedPreferences(sharedPreferences);
            editor.clear().commit();

            MMKV.mmkvWithID(SP_FILE_LIST_NAMES).putBoolean(spName, true);
        }
        return MMKV.mmkvWithID(spName);
    }

    @Nullable
    private String getString(String spName, String keyName, String defValue) {
        return MMKV.mmkvWithID(spName).getString(keyName, defValue);
    }

    /**
     * 兼容旧版sp，之前可能存过相关数据的情况下调用
     * 多一个转移导入的过程
     *
     * @param spName   String
     * @param keyName  String
     * @param defValue String
     * @return String
     */
    @Nullable
    private String getOldString(String spName, String keyName, String defValue) {
        importSpValue(spName);
        return getString(spName, keyName, defValue);
    }

    private boolean getBoolean(String spName, String keyName, Boolean defValue) {
        return MMKV.mmkvWithID(spName).getBoolean(keyName, defValue);
    }

    private boolean getOldBoolean(String spName, String keyName, Boolean defValue) {
        importSpValue(spName);
        return getBoolean(spName, keyName, defValue);
    }

    private float getFloat(String spName, String keyName, float defValue) {
        return MMKV.mmkvWithID(spName).getFloat(keyName, defValue);
    }

    private float getOldFloat(String spName, String keyName, float defValue) {
        importSpValue(spName);
        return getFloat(spName, keyName, defValue);
    }


    private int getInt(String spName, String keyName, int defValue) {
        return MMKV.mmkvWithID(spName).getInt(keyName, defValue);
    }

    private int getOldInt(String spName, String keyName, int defValue) {
        importSpValue(spName);
        return getInt(spName, keyName, defValue);
    }

    private long getLong(String spName, String keyName, long defValue) {
        return MMKV.mmkvWithID(spName).getLong(keyName, defValue);
    }

    private long getOldLong(String spName, String keyName, long defValue) {
        importSpValue(spName);
        return getLong(spName, keyName, defValue);
    }

    private byte[] getBytes(String spName, String keyName, @Nullable byte[] defValue) {
        return MMKV.mmkvWithID(spName).getBytes(keyName, defValue);
    }

    private byte[] getOldBytes(String spName, String keyName, @Nullable byte[] defValue) {
        importSpValue(spName);
        return getBytes(spName, keyName, defValue);
    }

    @Nullable
    private Set<String> getStringSets(String spName, String keyName, Set<String> defValue) {
        return MMKV.mmkvWithID(spName).getStringSet(keyName, defValue);
    }

    @Nullable
    private Set<String> getOldStringSets(String spName, String keyName, Set<String> defValue) {
        importSpValue(spName);
        return getStringSets(spName, keyName, defValue);
    }

    public void saveObj(String spName, String key, Object obj) {
        if (obj != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oout = new ObjectOutputStream(out);
                oout.writeObject(obj);
                String value = new String(Base64.encode(out.toByteArray(), Base64.DEFAULT));
                MMKV.mmkvWithID(spName).putString(key, value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MMKV.mmkvWithID(spName).remove(key);
        }
    }

    @Nullable
    public <T> T getObj(String spName, String key) {
        String value = MMKV.mmkvWithID(spName).getString(key, null);
        if (value != null) {
            byte[] valueBytes = Base64.decode(value, Base64.DEFAULT);
            ByteArrayInputStream bin = new ByteArrayInputStream(valueBytes);
            try {
                ObjectInputStream oin = new ObjectInputStream(bin);
                return (T) oin.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 退出mmkv
     */
    public void quitMMkv() {
        MMKV.onExit();
    }
}