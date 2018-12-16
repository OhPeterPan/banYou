package com.banyou.app.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns1:userloginResponse")
public class UserLoginResponse {
    public int returncode;
    public String returninfo;
    public String errormsg;
}
