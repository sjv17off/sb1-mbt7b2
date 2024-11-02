# Tasko - Task and Attendance Management System

## Deployment Instructions

### Windows Deployment

1. Prerequisites:
   - Install Java 11 or later
   - Install Maven

2. Build Steps:
   ```bash
   mvn clean package -DskipTests
   ```

3. Run the Application:
   ```bash
   java -jar target/tasko-0.0.1-SNAPSHOT.jar
   ```

4. Create Windows Executable:
   - Install Launch4j
   - Configure wrapper using the provided `launch4j-config.xml`
   - Generate EXE file

### Linux Deployment

1. Prerequisites:
   ```bash
   sudo apt update
   sudo apt install openjdk-11-jdk maven
   ```

2. Build Steps:
   ```bash
   mvn clean package -DskipTests
   ```

3. Create Service:
   ```bash
   sudo nano /etc/systemd/system/tasko.service
   ```

   Add the following content:
   ```
   [Unit]
   Description=Tasko Application
   After=network.target

   [Service]
   User=tasko
   ExecStart=/usr/bin/java -jar /path/to/tasko-0.0.1-SNAPSHOT.jar
   SuccessExitStatus=143

   [Install]
   WantedBy=multi-user.target
   ```

4. Start Service:
   ```bash
   sudo systemctl enable tasko
   sudo systemctl start tasko
   ```

## Access Application
- Open browser and navigate to: http://localhost:8080
- Default admin credentials: admin/password