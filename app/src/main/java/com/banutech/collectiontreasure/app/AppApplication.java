package com.banutech.collectiontreasure.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;

import com.banutech.collectiontreasure.service.TTSService;
import com.banutech.collectiontreasure.util.LogUtil;
import com.banutech.collectiontreasure.util.SystemUtil;
import com.banutech.collectiontreasure.util.TTSUtils;
import com.blankj.utilcode.util.Utils;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.jpush.android.api.JPushInterface;

public class AppApplication extends Application {
    private static Application app;

    public static Application getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Utils.init(this);

        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=5c0f5485");
        Setting.setShowLog(true); //设置日志开关（默认为true），设置成false时关闭语音云SDK日志打印
        TTSUtils.getInstance().init(); //初始化工具类

        JPushInterface.setDebugMode(SystemUtil.getDebug());
        JPushInterface.init(getApplicationContext());

        //开启一个前台服务
        ContextCompat.startForegroundService(getApplicationContext(), new Intent(getApplicationContext(), TTSService.class));
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                LogUtil.logI("app", "onActivityCreated()");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                LogUtil.logI("application", "onActivityStarted()");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                LogUtil.logI("application", "onActivityResumed()");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                LogUtil.logI("application", "onActivityPaused()");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                LogUtil.logI("application", "onActivityStopped()");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                LogUtil.logI("application", "onActivitySaveInstanceState()");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtil.logI("application", "onActivityDestroyed()");
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.logI("application", "这是啥");
    }
}
