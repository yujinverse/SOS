서버구조
파일구조


ble-spring-server/
├── build.gradle
├── settings.gradle
├── scan_ble.ps1
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── ble/
│   │   │               ├── BleSpringServerApplication.java
│   │   │               ├── controller/
│   │   │               │   └── BleController.java
│   │   │               ├── service/
│   │   │               │   └── BleService.java
│   │   │               └── model/
│   │   │                   └── BleDevice.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── ble/
│                       └── BleSpringServerApplicationTests.java
