## voucher-code-service
Sample service running as a mock HTTP API Server to simulate the following behaviours of a 3rd party voucher provider service:

```
* provide an API for the client to call via HTTP(s) protocol to get the voucher code
* The API always returns a voucher code after 3 to 120 seconds
```

### Run the service locally
To run the service locally, either
* Run the Spring Boot application with the main class VoucherCodeWorkerServiceApplication in an IDE (like IntelliJ)
* Open a terminal (Mac/Linux), or a cmd/Powershell window (Windows) at the working directory and run
```
mvnw spring-boot:run
```

### Run tests and collect coverage reports
To build the service as a runnable jar to deploy to any server, open a terminal (Mac/Linux), or a cmd/Powershell window (Windows) at the working directory and run
```
mvnw clean install
```
After the job is completed, the coverage report for the project can be view with the coverage report file [index.html](target/site/jacoco/index.html)

### Build the service as a runnable jar
To build the service as a runnable jar to deploy to any server, open a terminal (Mac/Linux), or a cmd/Powershell window (Windows) at the working directory and run
```
mvnw clean install
```
