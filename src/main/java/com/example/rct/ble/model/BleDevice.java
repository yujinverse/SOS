package com.example.rct.ble.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ble_devices")
public class BleDevice {

    @Id
    private String id;

    private String name;
    private String deviceId;
    private boolean connected; // 연결 상태 추가

    public BleDevice() {
    }

    public BleDevice(String name, String deviceId) {
        this.name = name;
        this.deviceId = deviceId;
        this.connected = false;
    }

    // Getters and Setters

    public String getId() {
        return id;
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

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public String toString() {
        return "BleDevice{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", connected=" + connected +
                '}';
    }
}
