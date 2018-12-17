package com.banyou.app.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns1:findOperatorListResponse")
public class OperatePersonResponse {
    public int returncode;
    public String returninfo;
    public String errormsg;
}

