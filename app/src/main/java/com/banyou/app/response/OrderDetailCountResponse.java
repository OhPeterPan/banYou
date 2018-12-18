package com.banyou.app.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns1:orderQuerySimpleCountResponse")
public class OrderDetailCountResponse {
    public int returncode;
    public String returninfo;
    public String errormsg;
}
