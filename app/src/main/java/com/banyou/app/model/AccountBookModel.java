package com.banyou.app.model;

import android.util.Log;

import com.banyou.app.common.IHttpClient;
import com.banyou.app.common.IRequest;
import com.banyou.app.common.IResponse;
import com.banyou.app.common.impl.BaseRequest;
import com.banyou.app.common.impl.BaseResponse;
import com.banyou.app.response.AccountBookResponse;
import com.banyou.app.rxBus.RxBus;
import com.thoughtworks.xstream.XStream;

import java.util.HashMap;

import io.reactivex.functions.Function;

public class AccountBookModel {
    private IHttpClient client;

    public AccountBookModel(IHttpClient client) {
        this.client = client;
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
                    XStream xStream = new XStream();
                    xStream.autodetectAnnotations(true);
                    xStream.processAnnotations(AccountBookResponse.class);
                    AccountBookResponse storeResponse = (AccountBookResponse) xStream.fromXML(result.trim());
                    return storeResponse;
                }
                return response;
            }
        });
    }
}
