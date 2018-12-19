package com.banutech.collectiontreasure.model;

import android.util.Log;

import com.banutech.collectiontreasure.common.IHttpClient;
import com.banutech.collectiontreasure.common.IRequest;
import com.banutech.collectiontreasure.common.IResponse;
import com.banutech.collectiontreasure.common.impl.BaseRequest;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.response.MainCountResponse;
import com.banutech.collectiontreasure.response.MainListResponse;
import com.banutech.collectiontreasure.rxBus.RxBus;
import com.thoughtworks.xstream.XStream;

import java.util.HashMap;

import io.reactivex.functions.Function;

public class MainInfoModel {
    private IHttpClient okHttpClient;

    public MainInfoModel(IHttpClient client) {
        okHttpClient = client;
    }

    public void sendNetQueryCount(final String date, final String startTime, final String endTime, final String companyId, final String fromType) {

        RxBus.getInstance().chain(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                HashMap hashMap = new HashMap<>();
                hashMap.put("date", date);
                hashMap.put("start", startTime);
                hashMap.put("end", endTime);
                hashMap.put("companyid", companyId);
                hashMap.put("fromType", fromType);
                IRequest request = new BaseRequest(hashMap, "orderQuerySimpleCount");
                IResponse response = okHttpClient.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    String result = response.getData();
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
                    Log.i("wak", result);
                    MainCountResponse mainResponse = null;
                    try {
                        XStream xStream = new XStream();
                        xStream.autodetectAnnotations(true);
                        xStream.processAnnotations(MainCountResponse.class);
                        mainResponse = (MainCountResponse) xStream.fromXML(result.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (mainResponse != null)
                        return mainResponse;
                    response.setException(new IllegalArgumentException("数据解析错误"));
                }
                return response;
            }
        });
    }

    public void sendNetQueryCount(final int page, final String date, final String startTime, final String endTime,
                                  final String companyId, final String fromType) {

        RxBus.getInstance().chain(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                HashMap hashMap = new HashMap<>();
                hashMap.put("date", date);
                hashMap.put("start", startTime);
                hashMap.put("end", endTime);
                hashMap.put("page", String.valueOf(page));
                hashMap.put("companyid", companyId);
                hashMap.put("fromType", fromType);
                IRequest request = new BaseRequest(hashMap, "orderQuerySimple");
                IResponse response = okHttpClient.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    // LogUtils.iTag(getClass().getSimpleName(), response.getData());
                    String result = response.getData();
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
                    Log.i("wak", result);
                    MainListResponse mainResponse = null;
                    try {
                        XStream xStream = new XStream();
                        xStream.autodetectAnnotations(true);
                        xStream.processAnnotations(MainListResponse.class);
                        mainResponse = (MainListResponse) xStream.fromXML(result.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (mainResponse != null)
                        return mainResponse;
                    response.setException(new IllegalArgumentException("数据解析错误"));
                }
                return response;
            }
        });
    }

    public void sendNetPay(final String payType, final String price) {
        RxBus.getInstance().chain(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                HashMap hashMap = new HashMap<>();
                hashMap.put("type_id", payType);
                hashMap.put("price", price);
                IRequest request = new BaseRequest(hashMap, "testPushmsg");
                IResponse response = okHttpClient.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    // LogUtils.iTag(getClass().getSimpleName(), response.getData());
                    String result = response.getData();
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
                    Log.i("wak", result);
                    MainListResponse mainResponse = null;
                    try {
                        XStream xStream = new XStream();
                        xStream.autodetectAnnotations(true);
                        xStream.processAnnotations(MainListResponse.class);
                        mainResponse = (MainListResponse) xStream.fromXML(result.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (mainResponse != null)
                        return mainResponse;
                    response.setException(new IllegalArgumentException("数据解析错误"));
                }
                return response;
            }
        });
    }
}
