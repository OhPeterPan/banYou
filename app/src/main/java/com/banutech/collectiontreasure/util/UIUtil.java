package com.banutech.collectiontreasure.util;

import android.content.Context;
import android.content.res.Resources;

import com.banutech.collectiontreasure.app.AppApplication;


public class UIUtil {
    public static Context getContext() {
        return AppApplication.getInstance().getApplicationContext();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    public static int getDimens(int resId) {
        return getResources().getDimensionPixelOffset(resId);
    }

    public static String[] getStringArray(int array) {
        return getResources().getStringArray(array);
    }

    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }
}
