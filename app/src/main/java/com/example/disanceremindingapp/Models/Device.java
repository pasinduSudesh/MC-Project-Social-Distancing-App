package com.example.disanceremindingapp.Models;

/**
 * For the Devices
 */

public class Device {
    private String deviceId;
    private String deviceName = null;
    private int rssi = 0;
    private boolean isSafeDevice = false;

    public Device(String id){
        this.deviceId = id;
    }

    public Device(String id, String name){
        this.deviceId = id;
        this.deviceName = name;
    }

    public void setDeviceName(String name){
        this.deviceName = name;
    }

    public void setSafeDevice(boolean safeDevice) {
        this.isSafeDevice = safeDevice;
    }

    public void setRSSI(int rssi){
        this.rssi = rssi;
    }

    public boolean isSafeDevice() {
        return isSafeDevice;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getRSSI() {
        return rssi;
    }
}
