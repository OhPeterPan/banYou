package com.banyou.app.model;

import android.util.Log;

import com.banyou.app.common.IHttpClient;
import com.banyou.app.common.IRequest;
import com.banyou.app.common.IResponse;
import com.banyou.app.common.impl.BaseRequest;
import com.banyou.app.common.impl.BaseResponse;
import com.banyou.app.response.OrderDetailCountResponse;
import com.banyou.app.response.OrderDetailListResponse;
import com.banyou.app.rxBus.RxBus;
import com.thoughtworks.xstream.XStream;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.functions.Function;

public class OrderDetailModel {
    private IHttpClient client;

    public OrderDetailModel(IHttpClient client) {
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
                IResponse response = client.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    String result = response.getData();
                    result = chainResult(result);
                    Log.i("wak", result);
                    XStream xStream = new XStream();
                    xStream.autodetectAnnotations(true);
                    xStream.processAnnotations(OrderDetailCountResponse.class);
                    OrderDetailCountResponse orderResponse = (OrderDetailCountResponse) xStream.fromXML(result.trim());
                    if (orderResponse != null)
                        return orderResponse;
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
                IResponse response = client.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    // LogUtils.iTag(getClass().getSimpleName(), response.getData());
                    String result = response.getData();
                    result = chainResult(result);
                    Log.i("wak", result);
                    XStream xStream = new XStream();
                    xStream.autodetectAnnotations(true);
                    xStream.processAnnotations(OrderDetailListResponse.class);
                    OrderDetailListResponse mainResponse = (OrderDetailListResponse) xStream.fromXML(result.trim());
                    if (mainResponse != null)
                        return mainResponse;
                    response.setException(new IllegalArgumentException("数据解析错误"));
                }
                return response;

            }
        });
    }
}
