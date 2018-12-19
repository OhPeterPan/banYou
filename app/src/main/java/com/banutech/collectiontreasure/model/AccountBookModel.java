package com.banutech.collectiontreasure.model;

import android.util.Log;

import com.banutech.collectiontreasure.common.IHttpClient;
import com.banutech.collectiontreasure.common.IRequest;
import com.banutech.collectiontreasure.common.IResponse;
import com.banutech.collectiontreasure.common.impl.BaseRequest;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.response.AccountBookResponse;
import com.banutech.collectiontreasure.response.OperatePersonResponse;
import com.banutech.collectiontreasure.response.OrderQueryResponse;
import com.banutech.collectiontreasure.rxBus.RxBus;
import com.thoughtworks.xstream.XStream;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.functions.Function;

public class AccountBookModel {
    private IHttpClient client;

    public AccountBookModel(IHttpClient client) {
        this.client = new WeakReference<>(client).get();
    }

    private String chianResult(String result) {
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

    public void sendNetStore(final String companyId, final String fromType) {
        RxBus.getInstance().chain(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                HashMap hashMap = new HashMap<>();
                hashMap.put("companyid", companyId);
                hashMap.put("fromType", fromType);
                IRequest request = new BaseRequest(hashMap, "findStoreinforList");
                IResponse response = client.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    String result = response.getData();
                    result = chianResult(result);
                    Log.i("wak", result);

                    AccountBookResponse storeResponse = null;
                    try {
                        XStream xStream = new XStream();
                        xStream.autodetectAnnotations(true);
                        xStream.processAnnotations(AccountBookResponse.class);
                        storeResponse = (AccountBookResponse) xStream.fromXML(result.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (storeResponse != null)
                        return storeResponse;
                    response.setException(new IllegalArgumentException("数据解析错误"));
                }
                return response;
            }
        });
    }

    public void sendNetOperatePerson(final String companyId, final String fromType) {
        RxBus.getInstance().chain(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                HashMap hashMap = new HashMap<>();
                hashMap.put("companyid", companyId);
                hashMap.put("fromType", fromType);
                IRequest request = new BaseRequest(hashMap, "findOperatorList");
                IResponse response = client.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    String result = response.getData();
                    result = chianResult(result);
                    Log.i("wak", result);

                    OperatePersonResponse personResponse = null;
                    try {
                        XStream xStream = new XStream();
                        xStream.autodetectAnnotations(true);
                        xStream.processAnnotations(OperatePersonResponse.class);
                        personResponse = (OperatePersonResponse) xStream.fromXML(result.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (personResponse != null)
                        return personResponse;
                    response.setException(new IllegalArgumentException("数据解析错误"));
                }
                return response;
            }
        });
    }

    public void sendNetTaking(final String searchDate, final String storeId,
                              final String operatorId, final String companyId, final String fromType) {
        RxBus.getInstance().chain(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                HashMap hashMap = new HashMap<>();
                hashMap.put("searchDate", searchDate);
                hashMap.put("storeId", storeId);
                hashMap.put("operatorId", operatorId);
                hashMap.put("companyid", companyId);
                hashMap.put("fromType", fromType);
                IRequest request = new BaseRequest(hashMap, "incomeDayQuery");
                IResponse response = client.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {
                    String result = response.getData();
                    result = chianResult(result);
                    Log.i("wak", result);
                    XStream xStream = new XStream();
                    xStream.autodetectAnnotations(true);
                    xStream.processAnnotations(OrderQueryResponse.class);
                    OrderQueryResponse orderResponse = (OrderQueryResponse) xStream.fromXML(result.trim());
                    return orderResponse;
                }
                return response;
            }
        });
    }
}
