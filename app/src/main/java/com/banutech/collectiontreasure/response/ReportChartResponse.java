package com.banutech.collectiontreasure.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns1:queryOrderChartResponse")
public class ReportChartResponse {
    public int returncode;
    public String returninfo;
    public String errormsg;
}
