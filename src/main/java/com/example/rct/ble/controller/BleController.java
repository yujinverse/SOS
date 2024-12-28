package com.example.rct.ble.controller;

import com.example.rct.ble.model.BleDevice;
import com.example.rct.ble.service.BleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ble")
public class BleController {

    @Autowired
    private BleService bleService;

    @GetMapping("/scan")
    public ResponseEntity<List<BleDevice>> scanBleDevices() {
        List<BleDevice> devices = bleService.scanBleDevices();
        if (!devices.isEmpty()) {
            return ResponseEntity.ok(devices);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/devices")
    public ResponseEntity<List<BleDevice>> getAllBleDevices() {
        List<BleDevice> devices = bleService.getAllBleDevices();
        if (!devices.isEmpty()) {
            return ResponseEntity.ok(devices);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/connect/{deviceId}")
    public ResponseEntity<String> connectToBleDevice(@PathVariable String deviceId) {
        boolean isConnected = bleService.connectBleDevice(deviceId);
        if (isConnected) {
            return ResponseEntity.ok("BLE 장치에 성공적으로 연결되었습니다.");
        } else {
            return ResponseEntity.status(500).body("BLE 장치 연결에 실패했습니다.");
        }
    }
}
