package com.banutech.collectiontreasure.model;

import android.util.Log;

import com.banutech.collectiontreasure.common.IHttpClient;
import com.banutech.collectiontreasure.common.IRequest;
import com.banutech.collectiontreasure.common.IResponse;
import com.banutech.collectiontreasure.common.impl.BaseRequest;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.response.ReportChartResponse;
import com.banutech.collectiontreasure.response.ReportFormsResponse;
import com.banutech.collectiontreasure.rxBus.RxBus;
import com.thoughtworks.xstream.XStream;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.functions.Function;

public class ReportFormsModel extends BaseModel{
    IHttpClient client;

    public ReportFormsModel(IHttpClient client) {
        this.client = new WeakReference<>(client).get();
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
                    result = parseXmlResult(result);
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
                    result = parseXmlResult(result);
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
