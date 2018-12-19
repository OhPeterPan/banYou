package com.banutech.collectiontreasure.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns1:incomeDayQueryResponse")
public class OrderQueryResponse {
    public int returncode;
    public String returninfo;
    public String errormsg;
}
