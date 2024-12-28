
$devices = Get-PnpDevice | Where-Object { $_.Class -eq "Bluetooth" }

$deviceList = $devices | Select-Object @{Name="name";Expression={$_.FriendlyName}}, @{Name="deviceId";Expression={$_.InstanceId}}

$deviceList | ConvertTo-Json
