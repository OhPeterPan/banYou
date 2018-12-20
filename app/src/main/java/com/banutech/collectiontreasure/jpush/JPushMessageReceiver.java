package com.banutech.collectiontreasure.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.banutech.collectiontreasure.rxBus.RxBus;
import com.blankj.utilcode.util.LogUtils;

import cn.jpush.android.api.JPushInterface;

public class JPushMessageReceiver extends BroadcastReceiver {
    private static String TAG = "JPushMessageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LogUtils.iTag(TAG, "JPush 用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.iTag(TAG, "接受到推送下来的自定义消息");
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.iTag(TAG, "接受到推送下来的通知");
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            startVoiceBroadcast(extras);
            //LogUtil.logI(TAG, "自定义通知!!" + extras);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.iTag(TAG, "用户点击打开了通知");

        } else {
            LogUtils.iTag(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void startVoiceBroadcast(String extras) {//{"order_code":"123456","price":"2000","type_id":"7","type_name":"支付宝"}  格式
        RxBus.getInstance().send(extras);
    }
}
