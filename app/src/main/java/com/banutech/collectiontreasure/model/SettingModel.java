package com.banutech.collectiontreasure.model;

import android.util.Log;

import com.banutech.collectiontreasure.common.IHttpClient;
import com.banutech.collectiontreasure.common.IRequest;
import com.banutech.collectiontreasure.common.IResponse;
import com.banutech.collectiontreasure.common.impl.BaseRequest;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.response.VoiceReceiverResponse;
import com.banutech.collectiontreasure.rxBus.RxBus;
import com.thoughtworks.xstream.XStream;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.functions.Function;

public class SettingModel {
    IHttpClient client;

    public SettingModel(IHttpClient client) {
        this.client = new WeakReference<>(client).get();
    }

    private String chainResult(String result) {
        result = result.replaceAll("&lt;", "<");
        result = result.replaceAll("&gt;", ">");
        result = result.replaceAll("<return>", "");
        result = result.replaceAll("</return>", "");
        result = result.replaceAll("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">", "");
        result = result.replaceAll("</soap:Envelope>", "");
        result = result.replaceAll("<soap:Header>", "");
        result = result.replaceAll("</soap:Header>", "");
        result = result.replaceAll("<soap:Body>", "");
        result = result.replaceAll("</soap:Body>", "");
        return result;
    }

    public void setVoiceBroadcast(final String id, final String userType, final String value) {
        RxBus.getInstance().chain(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                HashMap hashMap = new HashMap<>();
                hashMap.put("id", id);
                hashMap.put("userType", userType);
                hashMap.put("value", value);
                IRequest request = new BaseRequest(hashMap, "changeBroadcast");
                IResponse response = client.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    String result = response.getData();
                    result = chainResult(result);
                    Log.i("wak", result);
                    VoiceReceiverResponse orderResponse = null;
                    try {
                        XStream xStream = new XStream();
                        xStream.autodetectAnnotations(true);
                        xStream.processAnnotations(VoiceReceiverResponse.class);
                        orderResponse = (VoiceReceiverResponse) xStream.fromXML(result.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (orderResponse != null)
                        return orderResponse;
                    response.setException(new IllegalArgumentException("数据解析错误"));
                }
                return response;

            }
        });
    }

}
