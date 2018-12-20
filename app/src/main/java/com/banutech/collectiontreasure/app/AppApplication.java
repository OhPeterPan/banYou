package com.banutech.collectiontreasure.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;

import com.banutech.collectiontreasure.service.TTSService;
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
  /*      Album.initialize(
                AlbumConfig.newBuilder(UIUtil.getContext())
                        .setLocale(Locale.getDefault())
                        .build()
        );*/
        //开启一个前台服务
        ContextCompat.startForegroundService(getApplicationContext(), new Intent(getApplicationContext(), TTSService.class));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
