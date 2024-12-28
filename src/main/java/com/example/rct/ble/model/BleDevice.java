package com.example.rct.ble.model;

public class BleDevice {
    private String name;
    private String deviceId;

    public BleDevice() {
    }

    public BleDevice(String name, String deviceId) {
        this.name = name;
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "BleDevice{" +
                "name='" + name + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
