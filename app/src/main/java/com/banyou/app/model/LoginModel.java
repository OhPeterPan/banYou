package com.banyou.app.model;

import android.util.Log;

import com.banyou.app.common.IHttpClient;
import com.banyou.app.common.IRequest;
import com.banyou.app.common.IResponse;
import com.banyou.app.common.impl.BaseRequest;
import com.banyou.app.common.impl.BaseResponse;
import com.banyou.app.response.UserLoginResponse;
import com.banyou.app.rxBus.RxBus;
import com.thoughtworks.xstream.XStream;

import java.util.HashMap;

import io.reactivex.functions.Function;

public class LoginModel {

    private IHttpClient okHttpClient;

    public LoginModel(IHttpClient client) {
        okHttpClient = client;
    }

    public void sendLogin(final String userName, final String userPwd) {
        RxBus.getInstance().chain(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                HashMap<Object, Object> hashMap = new HashMap<>();
                hashMap.put("usernumber", userName);//"17601417313"
                hashMap.put("userpwd", userPwd);//"12345"
                IRequest request = new BaseRequest(hashMap, "userlogin");
                IResponse response = okHttpClient.post(request);
                if (response.getCode() == BaseResponse.CODE_SUCCESS) {//成功
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
                    xStream.processAnnotations(UserLoginResponse.class);
                    UserLoginResponse loginResponse = (UserLoginResponse) xStream.fromXML(result.trim());
                    return loginResponse;
                } else {
                    return response;
                }
            }
        });

    }
}
