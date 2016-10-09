# Instructions
To use this template, you will need to follow these steps:

1. `git init` to initialize Git
1. `git add --all` to put all files under source control
1. `git status` to make sure things look right
1. edit the `settings.gradle` file and replace the project name, eg `atlantis`
1. edit the `build.gradle` file, replacing the `group` and `description`
1. edit the `gradle.properties` file and reset the version properties to `1.0.0`
1. edit the `gradle.properties` and point it to your artifact server
1. edit the `README.md` file and put in a quick description, removing this `Instructions` section
1. run a quick build to verify that things compile, `./gradlew`
1. double check the output and see if it looks right, `ls build/libs`
1. edit the `gradle.properties` and point it to your Docker registry coordinates
1. edit the `bootstrap.yml` and point it to your Consul agent so a configuration server can be located
1. if you are publishing to [Docker Hub](), make sure to authenticate first via `docker login`
1. run the full build and verify things work, `bin/simulate-build-server.sh`
1. launch the application, `./gradlew bootRun`
1. check the `operations/info` endpoint, `http localhost:8080/operations/info`
1. check the `operations/health` endpoint, `http localhost:8080/operations/health`
1. check the `/descriptor/application` endpoint, `http localhost:8080/descriptor/application`
1. check the circuit breaker panel, in your browser open `http://localhost:8080/hystrix`, enter `http://localhost:8080/operations/hystrix.stream` as the URL
1. issue `http localhost:8080/descriptor/application` several times to tickle the circuit breaker panel
1. edit the `.gitignore` file and remove the `.idea` entry so you can save your IntelliJ files
1. `git status` to make sure things still look right
1. `git commit -am 'Initial check-in'` to save our work
1. import the `build.gradle` file into IntelliJ and create a new project
1. close the project
1. `cd .idea`
1. copy the `codeStyleSettings.xml` from another Groovy project, eg. `cp ~/GitHub/my-working-groovy-project/.idea/codeStyleSettings.xml .`
1. copy the copyright information from another Groovy project, eg. `cp -r ~/GitHub/my-working-groovy-project/.idea/copyright/ .`
1. `git add .idea`
1. re-open the project and make sure all the IntelliJ files are under Git
1. enable the newly added copyright via Settings->Editor->Copyright
1. commit to Git
1. Use IntelliJ to refactor the package name to suit your project
1. edit `build.gradle` and make `ext.coverageExcludes` an empty list 
1. `./gradlew`
1. check the new artifact coordinates, eg `ls ~/.m2/repository/org/kurron/atlantis/atlantis/1.0.0.RELEASE/`
1. commit your changes

----CUT HERE ----

# Overview
This project ...

# Prerequisites
* [JDK 8](http://www.oracle.com/technetwork/java/index.html) installed and working
* Building under [Ubuntu Linux](http://www.ubuntu.com/) is supported and recommended 

# Building
Type `./gradlew` to build and assemble the service.

# Installation
TODO

# Tips and Tricks

## Verifying The Setup
TODO

## Running Integration Tests From Gradle
TODO

## Running Acceptance Tests From Gradle
TODO

## Running Acceptance Tests From IDEA
TODO

## Operations Endpoints
The services supports a variety of endpoints useful to an Operations engineer.

* /operations - Provides a hypermedia-based “discovery page” for the other endpoints.
* /operations/autoconfig - Displays an auto-configuration report showing all auto-configuration candidates and the reason why they ‘were’ or ‘were not’ applied.
* /operations/beans - Displays a complete list of all the Spring beans in your application.
* /operations/configprops - Displays a collated list of all `@ConfigurationProperties`.
* /operations/dump - Performs a thread dump.
* /operations/env - Exposes properties from Spring’s `ConfigurableEnvironment`.
* /operations/flyway - Shows any `Flyway` database migrations that have been applied.
* /operations/health - Shows application health information.
* /operations/info - Displays arbitrary application info.
* /operations/liquibase - Shows any `Liquibase` database migrations that have been applied.
* /operations/logfile - Returns the contents of the logfile (if logging.file or logging.path properties have been set).
* /operations/metrics - Shows ‘metrics’ information for the current application.
* /operations/mappings - Displays a collated list of all `@RequestMapping` paths.
* /operations/shutdown - Allows the application to be gracefully shutdown (not enabled by default).
* /operations/trace - Displays trace information (by default the last few HTTP requests).

## REST API Documentation
You can find the current API documentation at `/docs/index.hml`.

## CORS Support
All origins are allowed.  Here is an example conversation:

```
http --verbose OPTIONS http://192.168.1.64:8080/descriptor/application Origin:nowhere.com Access-Control-Request-Method:DELETE

OPTIONS /descriptor/application HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Access-Control-Request-Method: DELETE
Connection: keep-alive
Content-Length: 0
Host: 192.168.1.64:8080
Origin: nowhere.com
User-Agent: HTTPie/0.9.6



HTTP/1.1 200
Access-Control-Allow-Credentials: true
Access-Control-Allow-Methods: GET,POST,DELETE,PUT,OPTIONS
Access-Control-Allow-Origin: nowhere.com
Access-Control-Max-Age: 1800
Allow: GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH
Content-Length: 0
Date: Sun, 09 Oct 2016 16:54:48 GMT
Vary: Origin
X-Application-Context: jvm-guy-spring-boot-project:8080
```

# Troubleshooting

TODO

# License and Credits
This project is licensed under the [Apache License Version 2.0, January 2004](http://www.apache.org/licenses/).

