package com.android.ll.znns.domain;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/12/19.
 */

public class FeedBackDomain extends BmobObject {

    private String content;
    private String address;
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
