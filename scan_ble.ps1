# scan_ble.ps1

# BLE 장치 검색
$devices = Get-PnpDevice -Class Bluetooth | Where-Object { $_.Status -eq "OK" }

# 필요한 정보만 선택
$bleDevices = foreach ($device in $devices) {
    $name = $device.FriendlyName
    $deviceId = $device.InstanceId
    if ($name) {
        [PSCustomObject]@{
            Name = $name
            DeviceID = $deviceId
        }
    }
}

# JSON 형식으로 출력
$bleDevices | ConvertTo-Json
