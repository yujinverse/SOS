package com.example.rct.ble.repository;

import com.example.rct.ble.model.BleDevice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BleDeviceRepository extends MongoRepository<BleDevice, String> {
    BleDevice findByDeviceId(String deviceId);
}
