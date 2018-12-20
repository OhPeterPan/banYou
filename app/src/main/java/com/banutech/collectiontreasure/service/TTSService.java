package com.banutech.collectiontreasure.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.banutech.collectiontreasure.bean.LoginBean;
import com.banutech.collectiontreasure.bean.PayResultMean;
import com.banutech.collectiontreasure.util.Convert;
import com.banutech.collectiontreasure.util.DecimalUtil;
import com.banutech.collectiontreasure.util.LogUtil;
import com.banutech.collectiontreasure.util.SpUtil;
import com.banutech.collectiontreasure.util.TTSUtils;
import com.banutech.collectiontreasure.util.ToastUtil;
import com.blankj.utilcode.util.StringUtils;

public class TTSService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // LogUtil.logI("wak", "创建服务?");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(Integer.MAX_VALUE, new Notification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // LogUtil.logI("wak", "开始服务?");
        String notification = intent.getStringExtra("notification");
        LoginBean account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        if (!StringUtils.isEmpty(notification) && TextUtils.equals("1", account.is_broadcast)) {
            LogUtil.logI("wak", "服务来吗?");
            PayResultMean payResultMean = Convert.fromJson(notification, PayResultMean.class);
            String result = new StringBuilder().append(payResultMean.type_name).append("收款").append(DecimalUtil.replaceZero(payResultMean.price)).append("元").toString();
            if (getStreamCurrentVolume() < getStreamMaxVolume() / 5) {
                ToastUtil.show("当前音量过小", Toast.LENGTH_SHORT);
            }
            TTSUtils.getInstance().speak(result);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private AudioManager getAudioManager() {
        return (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    private int getStreamCurrentVolume() {
        return getAudioManager().getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    private int getStreamMaxVolume() {
        return getAudioManager().getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // LogUtil.logI("wak", "回收服务?");
        TTSUtils.getInstance().release();//释放资源
    }
}
