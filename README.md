# <center>GBase Debug Tool</center>
## Project Structure
```bash
.
├── GbaseToolDemo.iml
├── META-INF
│   └── MANIFEST.MF
├── README.md
├── lib
│   └── gbase-connector-java-9.5.0.1-build1-bin.jar
├── out
│   ├── artifacts
│   │   └── GbaseToolDemo_jar
│   └── production
│       └── GbaseToolDemo
│           ├── META-INF
│           │   └── MANIFEST.MF
│           └── com
│               └── gbase
│                   ├── ConnectionTest.class
│                   └── Main.class
├── resource
│   └── connection.properties
└── src
    ├── META-INF
    │   └── MANIFEST.MF
    └── com
        └── gbase
            ├── ConnectionTest.class
            ├── Main.class
            ├── Main.java
            ├── controller
            │   ├── ChararacterSetController.java
            │   ├── ConnectionController.java
            │   ├── DriversController.java
            │   └── SqlController.java
            ├── dao
            │   ├── CharacterSetDao.java
            │   ├── ConnectionDao.java
            │   ├── DriversDao.java
            │   └── SqlDao.java
            ├── service
            │   ├── CharacterSetService.java
            │   ├── ConnectionService.java
            │   ├── DriversService.java
            │   ├── SqlService.java
            │   └── impl
            │       ├── CharacterSetServiceImpl.java
            │       ├── ConnectionServiceImpl.java
            │       ├── DriversServiceImpl.java
            │       └── SqlServiceImpl.java
            └── util
                ├── Login.java
                └── StringProcess.java
```