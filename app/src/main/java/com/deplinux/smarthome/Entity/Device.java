package com.deplinux.smarthome.Entity;

/**
 * Created by Landscape on 2016/5/30.
 */
public class Device {
    private int devId;
    private String devName;

    public Device() {
    }

    public Device(int devId, String devName) {
        this.devId = devId;
        this.devName = devName;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    @Override
    public String toString() {
        return "Device{" +
                "devId=" + devId +
                ", devName='" + devName + '\'' +
                '}';
    }
}
