## Setup

In root folder:

    mvn clean install

    java -jar target/demo-0.0.1-SNAPSHOT.jar
  
or option without database initialization:  
    
    java -Dspring.profiles.active=none -jar target/demo-0.0.1-SNAPSHOT.jar

## Profiles
Profile ***init-db*** is responsible for loading few employees into database (set by default)

## REST API Docs
http://localhost:8080/swagger-ui.html

Best sequence of endpoints invocation to verify API:

##### 1. POST /api/v1/employees
##### 2. PUT /api/v1/employees/{id}
##### 3. GET /api/v1/employees/{id}
##### 4. GET /api/v1/employees?yourParams
##### 5. DELETE /api/v1/employees/{id}

## Comments
For readability purposes I've changed type of 'grade' column to enum
