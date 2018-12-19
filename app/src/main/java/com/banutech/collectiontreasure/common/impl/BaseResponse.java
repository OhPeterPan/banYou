package com.banutech.collectiontreasure.common.impl;

import com.banutech.collectiontreasure.common.IResponse;

public class BaseResponse implements IResponse {

    public static int NET_EXCEPTION = 0x00110;//联网返回异常
    public static int CODE_SUCCESS = 200;//联网成功
    public static int RESULT_SUCCESS = 1;
    private int code;
    private String data;
    private Exception e;

    @Override
    public void setException(Exception e) {
        this.e = e;
    }

    @Override
    public Exception getException() {
        return e;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getData() {
        return data;
    }
}
