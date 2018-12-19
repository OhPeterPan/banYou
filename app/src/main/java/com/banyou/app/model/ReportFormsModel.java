package com.banyou.app.model;

import android.util.Log;

import com.banyou.app.common.IHttpClient;
import com.banyou.app.common.IRequest;
import com.banyou.app.common.IResponse;
import com.banyou.app.common.impl.BaseRequest;
import com.banyou.app.common.impl.BaseResponse;
import com.banyou.app.response.ReportChartResponse;
import com.banyou.app.response.ReportFormsResponse;
import com.banyou.app.rxBus.RxBus;
import com.thoughtworks.xstream.XStream;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.functions.Function;

public class ReportFormsModel {
    IHttpClient client;

    public ReportFormsModel(IHttpClient client) {
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
                IRequest request = new BaseRequest(hashMap, "queryOrderDate");
                IResponse response = client.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    String result = response.getData();
                    result = chainResult(result);
                    //Log.i("wak", result);
                    ReportFormsResponse orderResponse = null;
                    try {
                        XStream xStream = new XStream();
                        xStream.autodetectAnnotations(true);
                        xStream.processAnnotations(ReportFormsResponse.class);
                        orderResponse = (ReportFormsResponse) xStream.fromXML(result.trim());
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

    public void sendNetQueryCount(final String chartDate, final String companyId, final String fromType) {
        RxBus.getInstance().chain(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                HashMap hashMap = new HashMap<>();
                hashMap.put("date", chartDate);
                hashMap.put("companyid", companyId);
                hashMap.put("fromType", fromType);
                IRequest request = new BaseRequest(hashMap, "queryOrderChart");
                IResponse response = client.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    String result = response.getData();
                    result = chainResult(result);
                    Log.i("wak", result);
                    ReportChartResponse orderResponse = null;
                    try {
                        XStream xStream = new XStream();
                        xStream.autodetectAnnotations(true);
                        xStream.processAnnotations(ReportChartResponse.class);
                        orderResponse = (ReportChartResponse) xStream.fromXML(result.trim());
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
