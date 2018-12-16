package com.banyou.app.common;

public interface IResponse {


    void setException(Exception e);

    Exception getException();

    void setCode(int code);

    void setData(String data);

    int getCode();

    String getData();
}
