package com.banutech.collectiontreasure.util;

import android.app.ActivityManager;
import android.content.Context;

import com.banutech.collectiontreasure.ui.LoginActivity;

import java.util.List;

import cn.jpush.android.api.JPushInterface;


public class SystemUtil {
    private static boolean debug = true;

    public static void exitApp(Context ctx) {
        SpUtil.clearAll();
        LoginActivity.launchNewFlag(ctx);
        JPushInterface.deleteAlias(ctx, 1);//解除别名
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean getDebug() {
        return debug;
    }
}
