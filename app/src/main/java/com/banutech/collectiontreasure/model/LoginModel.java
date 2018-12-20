package com.banutech.collectiontreasure.model;

import android.util.Log;

import com.banutech.collectiontreasure.common.IHttpClient;
import com.banutech.collectiontreasure.common.IRequest;
import com.banutech.collectiontreasure.common.IResponse;
import com.banutech.collectiontreasure.common.impl.BaseRequest;
import com.banutech.collectiontreasure.common.impl.BaseResponse;
import com.banutech.collectiontreasure.response.UserLoginResponse;
import com.banutech.collectiontreasure.rxBus.RxBus;
import com.thoughtworks.xstream.XStream;

import java.util.HashMap;

import io.reactivex.functions.Function;

public class LoginModel extends BaseModel {

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
                    result = parseXmlResult(result);
                    Log.i("wak", result);
                    UserLoginResponse loginResponse = null;
                    try {
                        XStream xStream = new XStream();
                        xStream.autodetectAnnotations(true);
                        xStream.processAnnotations(UserLoginResponse.class);
                        loginResponse = (UserLoginResponse) xStream.fromXML(result.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (loginResponse != null)
                        return loginResponse;
                    response.setException(new IllegalArgumentException("数据解析错误"));
                }
                return response;
            }
        });

    }
}
