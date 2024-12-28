package com.example.rct.ble.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ble_devices")
public class BleDevice {

    @Id
    private String id;

    private String name;
    private String deviceId;

    public BleDevice() {
    }

    public BleDevice(String name, String deviceId) {
        this.name = name;
        this.deviceId = deviceId;
    }

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

    @Override
    public String toString() {
        return "BleDevice{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
