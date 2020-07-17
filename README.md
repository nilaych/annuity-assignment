# Annuity Calculator

### How to run using IDE
To run the application, please follow the steps mentioned below -
* Import the project in an IDE ___e.g. Eclipse / Intellij IDEA___
* Run the ___LendicoAssignmentApplication.java___

### How to run as an Spring Boot executable JAR
* Go to the project directory
* Build the project using the command ___mvn clean install___
* Execute the JAR using the command ___java -jar target/lendico-assignment-0.0.1-SNAPSHOT.jar___

### How to call the service
* Using a REST client (e.g. Postman), call the service http://localhost:8080/generate-plan with the below sample request as payload:
```
{
    "loanAmount": "5000",
    "nominalRate": "5.0",
    "duration": 24,
    "startDate": "2018-01-01T00:00:01Z"
}
```