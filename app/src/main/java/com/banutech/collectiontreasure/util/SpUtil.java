package com.banutech.collectiontreasure.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SpUtil {
    public static final String ACCOUNT = "account";
    public static final String POWER = "power";
    private static SharedPreferences sp;

    public static SharedPreferences getInstance() {
        synchronized (SpUtil.class) {
            if (sp == null) {
                sp = PreferenceManager.getDefaultSharedPreferences(UIUtil.getContext());
            }
        }
        return sp;
    }

    public static void save(String key, String value) {
        getInstance().edit().putString(key, value).commit();
    }

    public static void save(String key, Object o) {
        String s = Convert.toJson(o);
        save(key, s);
    }

    /**
     * 读取 k-v
     */
    public static String get(String key) {
        return getInstance().getString(key, null);
    }


    public static Object get(String key, Class cls) {
        String value = get(key);
        try {
            if (value != null) {
                Object o = Convert.fromJson(value, cls);
                return o;
            }
        } catch (Exception e) {

        }

        return null;
    }

    public static void clearAll() {
        getInstance().edit().clear().commit();
    }
}
