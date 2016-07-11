package com.deplinux.smarthome.Entity;

import java.io.Serializable;

/**
 * Created by Landscape on 2016/6/4.
 * 配置信息实体
 */
public class Config implements Serializable {
    private String phoneNum;
    private String outerIP;
    private String innerIP;

    public Config() {
    }

    public Config(String phoneNum, String outerIP, String innerIP) {
        this.phoneNum = phoneNum;
        this.outerIP = outerIP;
        this.innerIP = innerIP;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getOuterIP() {
        return outerIP;
    }

    public void setOuterIP(String outerIP) {
        this.outerIP = outerIP;
    }

    public String getInnerIP() {
        return innerIP;
    }

    public void setInnerIP(String innerIP) {
        this.innerIP = innerIP;
    }

    @Override
    public String toString() {
        return "Config{" +
                "phoneNum='" + phoneNum + '\'' +
                ", outerIP='" + outerIP + '\'' +
                ", innerIP='" + innerIP + '\'' +
                '}';
    }
}
