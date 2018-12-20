package com.banutech.collectiontreasure.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseModel {
    public String parseXmlResult(String result) {
        result = result.replaceAll("&lt;", "<");
        result = result.replaceAll("&gt;", ">");
        result = result.replaceAll("<return>", "");
        result = result.replaceAll("</return>", "");

        String regex = "<" + "ns1:(.*?)" + ">(.*?)</" + "ns1:(.*?)" + ">";
        try {
            Pattern pattern = Pattern.compile(regex);// 匹配的模式
            Matcher m = pattern.matcher(result);
            result = m.group(0);
        } catch (Exception e) {//正则匹配失败后
            // e.printStackTrace();
            result = result.replaceAll("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">", "");
            result = result.replaceAll("</soap:Envelope>", "");
            result = result.replaceAll("<soap:Header>", "");
            result = result.replaceAll("</soap:Header>", "");
            result = result.replaceAll("<soap:Body>", "");
            result = result.replaceAll("</soap:Body>", "");
        }

        return result;
    }
}
