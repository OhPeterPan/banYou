package com.banutech.collectiontreasure.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns1:findStoreinforListResponse")
public class AccountBookResponse {
    public int returncode;
    public String returninfo;
    public String errormsg;
}

