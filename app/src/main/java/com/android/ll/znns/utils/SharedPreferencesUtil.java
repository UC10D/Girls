package com.android.ll.znns.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by LeeMy on 2016/12/19 0019.
 * SharedPreferences 工具类
 */
public class SharedPreferencesUtil {
    private final static String SP_NAME = "pb_config";
    private static SharedPreferences sharedPreferences = null;
    private static boolean shouldCommit = Integer.parseInt(Build.VERSION.SDK) < 9;

    private static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences =
                    context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    private static void save(SharedPreferences.Editor editor) {
        if (shouldCommit) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        save(getSharedPreferences(context).edit().putBoolean(key, value));
    }

    public static void saveInt(Context context, String key, int value) {
        save(getSharedPreferences(context).edit().putInt(key, value));
    }

    public static void saveLong(Context context, String key, Long value) {
        save(getSharedPreferences(context).edit().putLong(key, value));
    }

    public static void saveString(Context context, String key, String value) {
        save(getSharedPreferences(context).edit().putString(key, value));
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static int getInt(Context context, String key, int defValue) {
        return getSharedPreferences(context).getInt(key, defValue);
    }

    public static Long getLong(Context context, String key, Long defValue) {
        return getSharedPreferences(context).getLong(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static void removeValue(Context context, String key) {
        save(getSharedPreferences(context).edit().remove(key));
    }

}
