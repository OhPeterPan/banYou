package com.banutech.collectiontreasure.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.banutech.collectiontreasure.util.SystemUtil;
import com.banutech.collectiontreasure.util.TTSUtils;
import com.banutech.collectiontreasure.util.UIUtil;
import com.blankj.utilcode.util.Utils;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.Locale;

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
        Album.initialize(
                AlbumConfig.newBuilder(UIUtil.getContext())
                        .setLocale(Locale.getDefault())
                        .build()
        );
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
