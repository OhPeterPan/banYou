package com.banutech.collectiontreasure.common.impl;

import com.banutech.collectiontreasure.common.IRequest;

import java.util.HashMap;
import java.util.Set;

public class BaseRequest implements IRequest {
    private HashMap<String, String> params;
    private String method;
    private String namespace = "http://webService.youzi_ws.jetinfor.com/";

    public BaseRequest(HashMap params, String method) {
        this.params = params;
        this.method = method;
    }

    @Override
    public String createNetSoapText() {

        StringBuffer buf = new StringBuffer();
        Set<String> tags = params.keySet();
        buf.append("<?xml version=\"1.0\" ?>");
        buf.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        buf.append("<S:Header>");
        buf.append("<SllmWebService xmlns=\"" + namespace + "\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" SOAP-ENV:actor=\"http://www.w3.org/2003/05/soap-envelope/role/next\">");
        buf.append("admin" + "&amp;" + "admin");
        buf.append("</SllmWebService>");
        buf.append("</S:Header>");
        buf.append("<S:Body>");
        buf.append("<ns2:" + method + " xmlns:ns2=\"" + namespace + "\">");

        for (String tag : tags) {
            buf.append("<" + tag + ">");
            buf.append(params.get(tag));
            buf.append("</" + tag + ">");
        }

        buf.append("</ns2:" + method + ">");
        buf.append("</S:Body>");
        buf.append("</S:Envelope>");

        return buf.toString();
    }
}
