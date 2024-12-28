param (
    [string]$DeviceId
)

Write-Output "Activating BLE device: $DeviceId"
Enable-PnpDevice -InstanceId $DeviceId -Confirm:$false


Write-Output "BLE device connected: $DeviceId"
exit 0

