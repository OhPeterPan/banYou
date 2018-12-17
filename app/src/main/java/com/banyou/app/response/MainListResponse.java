package com.banyou.app.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns1:orderQuerySimpleResponse")
public class MainListResponse {
    public int returncode;
    public String returninfo;
    public String errormsg;
}
