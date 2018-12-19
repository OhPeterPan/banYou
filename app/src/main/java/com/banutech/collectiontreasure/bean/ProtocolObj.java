package com.banutech.collectiontreasure.bean;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import java.io.Serializable;

public class ProtocolObj implements Serializable {
    public String toXML() {
        // 1. 创建xStream对象
        XStream x = new XStream();
        x.alias(this.getClass().getSimpleName(), this.getClass());
        // toXML  ctrl + alt +V
        String xml = x.toXML(this);
        return xml;

    }

    public ProtocolObj fromXML(String xml) {
        // 1. 创建xStream对象
        XStream x = new XStream();
        x.alias(this.getClass().getSimpleName(), this.getClass());
        return (ProtocolObj) x.fromXML(xml);
    }


    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public ProtocolObj fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, this.getClass());
    }
}