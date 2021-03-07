## sim-data-service
Main sample service running as the HTTP API servers to provide functions for the sim data function for bank ABC

Also acts as a Redisson Distributed Scheduled Executor Service to submit, execute and listen for results for distributed computations with remote workers

Please reference [Redisson Distributed Scheduled Executor Service wiki](https://github.com/redisson/redisson/wiki/9.-distributed-services#94-distributed-scheduled-executor-service) for more details

### Preconditions
* Java 8
* An instance of Redis server is started in the local machine with default port (6379) in a single node setup (Guide [here](https://redislabs.com/get-started-with-redis/))
* If Redis server is not running at the default config then you need to change the file [redisson config file](src/main/resources/redisson-config.yaml) following [Configuration](https://github.com/redisson/redisson/wiki/2.-Configuration) page

### Run the service locally
To run the service locally, either
* Run the Spring Boot application with the main class VoucherCodeWorkerServiceApplication in an IDE (like IntelliJ)
* Open a terminal (Mac/Linux), or a cmd/Powershell window (Windows) at the working directory and run
```shell
mvnw spring-boot:run
```

### Run tests and collect coverage reports
To build the service as a runnable jar to deploy to any server, open a terminal (Mac/Linux), or a cmd/Powershell window (Windows) at the working directory and run
```shell
mvnw clean install
```
After the job is completed, the coverage report for the project can be view with the coverage report file [index.html](target/site/jacoco/index.html)

### Build the service as a runnable jar
To build the service as a runnable jar to deploy to any server, open a terminal (Mac/Linux), or a cmd/Powershell window (Windows) at the working directory and run
```shell
mvnw clean install
```
