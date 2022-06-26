# Synalogik Word-Count Application

## Purpose

An API to read the contents of a plain text file and enable the display of the
total number of words, the average word length, the most frequently occurring word length, and a
list of the number of words of each length.

### Requirements

For building and running the application you need:

* [JDK 11](https://www.oracle.com/uk/java/technologies/javase/jdk11-archive-downloads.html)
* [Maven 3](https://maven.apache.org/)
* [Lombok Plugin](https://plugins.jetbrains.com/plugin/6317-lombok)

## Running the application locally:

#### Start the Spring-Boot Application:

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method
SynalogikWordCountApplication class from your IDE.

Alternatively you can use the Maven commands from the application folder or from IDE Maven Tab:

```bash
mvn clean install
mvn spring-boot:run
```

Request Format:

```bash
HTTP Method = POST
Request URI = /word-count
Headers = [Content-Type:"multipart/form-data"]
Body = Multipart Form, Input = fileName, Value = Browse and select a file
```

#### Swagger Details: [Swagger End-Point](http://localhost:8080/synalogik/v1/swagger-ui.html)

_____________________________________________________________________________________________________________________________________________________________

##### API End Point details (Local Profile):

Operation|API End Point|Details
--------------|--------------|--------------------
GET|http://localhost:8080/synalogik/v1/actuator|Actuator
GET|http://localhost:8080/synalogik/v1/actuator/health|Actuator Health
GET|http://localhost:8080/synalogik/v1/actuator/metrics|Actuator Metrics
GET|http://localhost:8080/synalogik/v1/actuator/env|Actuator Environment
POST| http://localhost:8080/synalogik/v1/word-count|Word-Count

_____________________________________________________________________________________________________________________________________________________________

#### Note:

* Unit tests has been written for Controller and Service classes
* Formatted numbers input has been considered and handled in the code
* Swagger end-point can be used to test the application
* ASCII value of the characters is not validated. The application is modeled to work with all kind of special
  characters.