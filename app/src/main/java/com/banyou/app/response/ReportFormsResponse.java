package com.banyou.app.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns1:queryOrderDateResponse")
public class ReportFormsResponse {
    public int returncode;
    public String returninfo;
    public String errormsg;
}
