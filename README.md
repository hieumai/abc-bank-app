## abc-bank-app
Sample application for provide the service for purchasing prepaid SIM data of ABC bank

### Folder structure
```
abc-bank-app
├───diagrams                        # contains all the technical diagrams for the projects
├───sim-data-service                # main service - provide sim data API functions
├───voucher-code-service            # mock 3rd party voucher code service
└───voucher-code-worker-service     # worker service to run as distributed job to integrate with 3rd party voucher code service
```

### Applied software principles, patterns & practices
* [Three-tier architecture](https://en.wikipedia.org/wiki/Multitier_architecture#Three-tier_architecture) in sim-data-service
* [Distributed Asynchronous Service Architecture using Redisson](https://github.com/redisson/redisson/wiki/9.-distributed-services)
* Inversion of Control principals using Spring Dependency Injection
* \>90% coverage of Unit Tests

### Technology stacks and reasons for choosing
* Java 8: Recommended [most widely used](https://snyk.io/blog/developers-dont-want-to-leave-java-8-as-64-hold-firm-on-their-preferred-release/) version of Java
* Spring Framework (Spring Boot + Web + Security + JPA): [most popular](https://snyk.io/blog/jvm-ecosystem-report-2018-platform-application/) open-source framework platform for enterprise Java Applications
* SQL Database using H2: fast and embedded database engine allow for quick development and testing for the project in this phase
* Redis: open source in-memory data structure store that can be used as a database to store short-lived data (like a sms verification code) with auto expiration or as a message broker that can be integrated with Redisson to be used in a Distributed Service Architecture System
* JUnit 5: Newest version of JUnit provide new & useful features while solving older versions' limitations

### Quick start guide
From the current working directory, use the following steps on quickly start a local development environment

1. Install and start Redis Server on the local machine following this [guide](https://redislabs.com/get-started-with-redis/) with the default configuration
2. Open a terminal/cmd/Powershell window in the directory [voucher-code-service](voucher-code-service) and execute the following command to start the **voucher-code-service** at the url http://localhost:4000
```shell
mvnw spring-boot:run
```
3. Open a terminal/cmd/Powershell window in the directory [voucher-code-worker-service](voucher-code-worker-service) and execute the following command to start an instance of the **voucher-code-worker-service** at port 5000
```shell
mvnw spring-boot:run
```
4. Open a terminal/cmd/Powershell window in the directory [sim-data-service](sim-data-service) and execute the following command to start an instance of the **sim-data-service** at the url http://localhost:8080
```shell
mvnw spring-boot:run
```

### Sample test cases with CURL and seed data

#### Case #1: Existing User - already have a password
1. Run the following command to get a new voucher code
```shell
curl -X POST -F "phoneNumber=09332229321" http://localhost:8080/api/v1/data
```
The result will either be the **purchased voucher code**, or a message saying "Please wait. The request is being processed within 30 seconds"  
If the result is the voucher code then skip to step #3

2. If step #1 result in the waiting message, after waiting for maximum of 150 seconds, go to the terminal/cmd/Powershell window that run the process **sim-data-service**, and you should see a log message like below
> SMS sent for voucher code <random code> to 09332229321

This simulates the sms message being sent to the user after the **purchased voucher code** is received from the worker service

3. Run the following command to view the purchased voucher codes for phone number **09332229321**
```shell
curl -u 09332229321:password localhost:8080/api/v1/vouchers?phoneNumber=09332229321
```
You should see the following results return from the http call
```json
["fa810ba5-2ec7-44d9-be92-fd99799bcb10","834d979a-0ddc-4214-b1f4-e0ce39fce330","<code from step #1 or #2>"]
``` 

#### Case #2: New User - password is null
1. Run the following command to get a new voucher code
```shell
curl -X POST -F "phoneNumber=01234566789" http://localhost:8080/api/v1/data
```
The result will either be the **purchased voucher code**, or a message saying "Please wait. The request is being processed within 30 seconds"  
If the result is the voucher code then skip to step #3

A new user should be created for the input phone number

2. If step #1 result in the waiting message, after waiting for maximum of 150 seconds, go to the terminal/cmd/Powershell window that run the process **sim-data-service**, and you should see a log message like below
> SMS sent for voucher code <random code> to 01234566789

This simulates the sms message being sent to the user after the **purchased voucher code** is received from the worker service

3. Next step is to generate a new password for our new user. Run the following command to generate a sms verification code sent to the phone number **01234566789**
```shell
curl localhost:8080/api/v1/sms/verificationCode?phoneNumber=01234566789
```
You should see the follow message, or log from the **sim-data-service** simulate our **generated sms verification code**:
> Your login verification code is:<sms verification code> - valid for 60 seconds

The code has only 60 second expiration time so be sure to use it quickly in the next step

4. Run the following command to set a new password for our new user
```shell
curl -X PATCH -F "phoneNumber=01234566789" -F "newPassword=newPassword" -F "verificationCode=<sms verification code>" localhost:8080/api/v1/users/resetPassword
```
The API request should return a 202 Accepted status code to indicate the reset password process is successfully

5. Run the following command to use the phone number, and the new password to view the purchased voucher codes for our new user
```shell
curl -u 01234566789:newPassword localhost:8080/api/v1/vouchers?phoneNumber=01234566789
```
You should see the following result return from the http call
```json
["<code from step #1 or #2>"]
```