package com.banutech.collectiontreasure.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns1:changeBroadcastResponse")
public class VoiceReceiverResponse {
    public int returncode;
    public String returninfo;
    public String errormsg;
}

