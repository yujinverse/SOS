param (
    [string]$DeviceId
)

$device = Get-PnpDevice | Where-Object { $_.InstanceId -eq $DeviceId }

if ($device) {

    Enable-PnpDevice -InstanceId $DeviceId -Confirm:$false

    Write-Output "BLE 장치에 연결 중: $DeviceId"

    exit 0
} else {
    Write-Output "BLE 장치를 찾을 수 없습니다: $DeviceId"
    exit 1
}
