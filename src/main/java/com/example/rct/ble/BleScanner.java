package com.example.rct.ble;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BleScanner {

    public List<BleDevice> scanDevices() {
        List<BleDevice> devices = new ArrayList<>();
        try {

            ProcessBuilder builder = new ProcessBuilder(
                    "powershell.exe",
                    "-ExecutionPolicy",
                    "Bypass",
                    "-File",
                    "C:\\path\\to\\scan_ble.ps1"
            );
            builder.redirectErrorStream(true);
            Process process = builder.start();


            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            reader.readLine();
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\s{2,}"); // 여러 공백으로 분리
                    if (parts.length >= 2) {
                        String name = parts[0].trim();
                        String deviceId = parts[1].trim();
                        devices.add(new BleDevice(name, deviceId));
                    }
                }
            }

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devices;
    }

    public static void main(String[] args) {
        BleScanner scanner = new BleScanner();
        List<BleDevice> devices = scanner.scanDevices();
        for (BleDevice device : devices) {
            System.out.println(device);
        }
    }
}

class BleDevice {//
    private String name;
    private String deviceId;

    public BleDevice(String name, String deviceId) {
        this.name = name;
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "BleDevice{name='" + name + "', deviceId='" + deviceId + "'}";
    }


}

