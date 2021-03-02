## voucher-code-worker-service
Sample service running as a Redisson node as workers for long-running tasks to integrate with 3rd party voucher code services

Please reference [Redisson Standalone Node wiki](https://github.com/redisson/redisson/wiki/12.-Standalone-node) for more details about Redisson node

### Preconditions
* An instance of Redis server is started in the local machine with default port (6379)
* If Redis server is not running at the local machine or not a single node setup then change the file [redisson config file](src/main/resources/redisson-config.yaml) following [Configuration](https://github.com/redisson/redisson/wiki/2.-Configuration) page

### Run the service locally
To run the service locally, either
* Run the Spring Boot application with the main class VoucherCodeWorkerServiceApplication in an IDE (like IntelliJ)
* Open a terminal (Mac/Linux), or a cmd/Powershell window (Windows) at the working directory and run
```
mvnw spring-boot:run
```

### Build the service as a runnable jar
To build the service as a runnable jar to deploy to any server, open a terminal (Mac/Linux), or a cmd/Powershell window (Windows) at the working directory and run
```
mvnw clean install
```
