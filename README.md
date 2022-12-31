# Choice University Exercice

The Choice University Exercise is an app that consists of 3 modules:
Description Create two spring boot applications that together allow the user to store and read from a catalog of products through a REST API Modules 1.

1.  Java App with Spring 5. A Spring App exposing a REST API.
2.  Microservice. A Spring Boot App exposing the business operations using SOAP and storing data in the database.
3.  DataBase. A Postgresql DB that store the information from the microservice.

## Built with

- [Docker](https://www.docker.com)
- [Tomcat 9](https://tomcat.apache.org/index.html)
- [Maven 3](https://maven.apache.org)

## Instalation

### Rest API

Add tomcat user on `[tomcat_dir]/conf/tomcat-users.xml` to deploy war file

```xml
<tomcat-users xmlns="http://tomcat.apache.org/xml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd" version="1.0">
    <user username="war-deployer" password="maven-tomcat" roles="manager-gui, manager-script, manager-jmx" />
    ...
</tomcat-users>
```

Add Server to Maven on `[maven_dir]/conf/settings.xml`

```xml
<servers>
    <server>
        <id>maven-tomcat-war-deployment-server</id>
        <username>war-deployer</username>
        <password>maven-tomcat</password>
    </server>
    ...
</servers>
```

### Development
### REST API
Deploy spring app to tomacat
```bash
mvn clean tomcat7:deploy
    or
mvn clean tomcat7:redeploy
```
### SOAP API and Database
Start docker
```bash
docker-compose up
```
