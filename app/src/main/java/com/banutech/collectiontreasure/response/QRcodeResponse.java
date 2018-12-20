package com.banutech.collectiontreasure.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns1:getQRcodeResponse")
public class QRcodeResponse {
    public int returncode;
    public String returninfo;
    public String errormsg;
}
