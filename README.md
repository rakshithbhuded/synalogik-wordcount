# Synalogik Word-Count Application

## Purpose

An API to read the contents of a plain text file and enable the display of the
total number of words, the average word length, the most frequently occurring word length, and a
list of the number of words of each length.

_____________________________________________________________________________________________________________________________________________________________

### Requirements:

For building and running the application you need:

* [JDK 11](https://www.oracle.com/uk/java/technologies/javase/jdk11-archive-downloads.html)
* [Maven 3](https://maven.apache.org/)
* [Lombok Plugin](https://plugins.jetbrains.com/plugin/6317-lombok)

_____________________________________________________________________________________________________________________________________________________________

### Application has been deployed in <span style="color: green"> Microsoft Azure App</span>: [Actuator Health End-Point](https://app-synalogik-wordcount.azurewebsites.net/synalogik/v1/actuator/health)

_____________________________________________________________________________________________________________________________________________________________

## Running the application locally:

#### Start the Spring-Boot Application:

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method
SynalogikWordCountApplication class from your IDE.

Alternatively you can use the Maven commands from the application folder or from IDE Maven Tab:

```bash
mvn clean install
mvn spring-boot:run
```

_____________________________________________________________________________________________________________________________________________________________

#### Request Format ( New request using Insomnia or Postman ):

```text
HTTP Method = POST
Headers = [Content-Type:"multipart/form-data"]
Body = Multipart Form, Input = fileName, Value = Browse and select a file
```

_____________________________________________________________________________________________________________________________________________________________

#### Swagger Details: [Swagger End-Point](http://localhost:8080/synalogik/v1/swagger-ui.html)

_____________________________________________________________________________________________________________________________________________________________

##### API End Point details:

| Operation |                               API End Point                               |       Details        |
|:---------:|:-------------------------------------------------------------------------:|:--------------------:|
|   POST    |               http://localhost:8080/synalogik/v1/word-count               |      Word-Count      |
|   POST    | https://app-synalogik-wordcount.azurewebsites.net/synalogik/v1/word-count |   Azure End-Point    |
|    GET    |                http://localhost:8080/synalogik/v1/actuator                |       Actuator       |
|    GET    |            http://localhost:8080/synalogik/v1/actuator/health             |   Actuator Health    |
|    GET    |            http://localhost:8080/synalogik/v1/actuator/metrics            |   Actuator Metrics   |
|    GET    |              http://localhost:8080/synalogik/v1/actuator/env              | Actuator Environment |

_____________________________________________________________________________________________________________________________________________________________

#### Note:

* Swagger end-point can be used to interact with the Application
* Code has been deployed in Microsoft Azure web-apps (Instance size is small)
* ASCII value of the characters in the input file is not validated. The application is modeled to work with all special
  characters
* Unit & Integration tests has been added