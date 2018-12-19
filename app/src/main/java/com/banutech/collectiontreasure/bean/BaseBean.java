package com.banutech.collectiontreasure.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("soap:Envelope")
public class BaseBean {
    @XStreamAlias("soap:Body")
    public static class Body {

    }
}
