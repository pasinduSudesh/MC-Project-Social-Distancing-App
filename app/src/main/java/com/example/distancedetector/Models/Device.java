package com.example.distancedetector.Models;

/**
 * For the Devices
 */

public class Device {
    private String deviceId;
    private String deviceName = null;
    private int rssi = 0;
    private boolean isSafeDevice = false;
    private long lastDetected ;
    private double distance;

    public Device(String id){
        this.deviceId = id;
    }

    public Device(String id, String name){
        this.deviceId = id;
        this.deviceName = name;
    }

    public Device(String id, String name, int isSafe, long lastDetected){
        this.deviceId = id;
        this.deviceName = name;
        if (isSafe ==1){
            isSafeDevice = true;
        }
        this.lastDetected = lastDetected;
    }

    public Device(String id, String name, int isSafe, long lastDetected, double distance){
        this.deviceId = id;
        this.deviceName = name;
        if (isSafe ==1){
            isSafeDevice = true;
        }
        this.lastDetected = lastDetected;
        this.distance= distance;
    }

    public long getLastDetected() {
        return lastDetected;
    }

    public void setLastDetected(long lastDetected) {
        this.lastDetected = lastDetected;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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
