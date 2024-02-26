# CO2 Monitoring Service
## Overview

The CO2 Monitoring Service is designed to collect data from hundreds of thousands of sensors and alert if the CO2 concentrations reach critical levels. It supports receiving measurements at a rate of one per minute per sensor, updating sensor statuses based on CO2 levels, calculating average and maximum CO2 levels over the last 30 days, and listing all alerts for a given sensor.

## Features

 - Data Collection: Receives CO2 concentration measurements from sensors.
 - Alerting: Updates sensor status to WARN or ALERT based on CO2 levels, and stores alerts for high concentration levels.
 - Metrics Calculation: Provides average and maximum CO2 level metrics for the last 30 days.
 - Alert Listing: Lists all alerts for a specific sensor.

## Prerequisites

 - Java JDK 11 or later
 - Maven 3.6.0 or later (for building the project)

Creating an [OpenAPI 3.0.0](https://spec.openapis.org/oas/v3.0.0) Specification for CO2 Monitoring service.

We have to split the OpenAPI documents into many yaml files and reference those files later is an approach to help us during the design process, avoiding duplications and enhance code reuse.

## Redocly CLI
[Redocly CLI](https://redocly.com/docs/cli/) is an open source command-line tool that makes it easier to work with OpenAPI definition files.

Install using [npm](https://docs.npmjs.com/about-npm/):

``` bash
npm i -g @redocly/cli@latest
```

### Version
``` bash
redocly --version
```
Output:
```
1.0.0-beta.124
```

### Usage
``` bash
redocly --help
```
Output *(Dev can use following commands according to requirement)*:
```
redocly <command>

Commands:
  redocly stats [api]                       Gathering statistics for a document.
  redocly split [api]                       Split definition into a multi-file
                                            structure.
  redocly join [apis...]                    Join definitions [experimental].
  redocly push [maybeApiOrDestination]      Push an API definition to the
  [maybeDestination] [maybeBranchName]      Redocly API registry.
  redocly lint [apis...]                    Lint definition.
  redocly bundle [apis...]                  Bundle definition.
  redocly login                             Login to the Redocly API registry
                                            with an access token.
  redocly logout                            Clear your stored credentials for
                                            the Redocly API registry.
  redocly preview-docs [api]                Preview API reference docs for the
                                            specified definition.
  redocly build-docs [api]                  build definition into
                                            zero-dependency HTML-file
  redocly completion                        Generate completion script.

Options:
  --version  Show version number.                                      [boolean]
  --help     Show help.                                                [boolean]
```

### Lint
Go to the project root `cd ms-co2-sensors`.

To identify and report on problems found in OpenAPI definitions. In case of valid OpenAPI definition you will get the following response:

``` bash
redocly lint src/main/resources/specs/co2-sensors.yml
```
Output:
```
No configurations were provided -- using built in recommended configuration by default.

validating src/main/resources/specs/co2-sensors.yml...
src/main/resources/specs/co2-sensors.yml: validated in 68ms

Woohoo! Your OpenAPI definition is valid. 🎉
```

### Html doc
To build the html doc run the following:

``` bash
redocly build-docs src/main/resources/specs/co2-sensors.yml
```
Output:
```
Found undefined and using theme.openapi optionsPrerendering docs
🎉 bundled successfully in: redoc-static.html (179 KiB) [⏱ 13ms].
```

# Bundle
To bundle the reusable parts as separate files, and include them in the main (root) API definition by referencing them with `$ref`. Go to the project root `cd ms-co2-sensors` folder and run the following:

``` bash
 redocly bundle src/main/resources/specs/co2-sensors.yml -o bundled.yaml
```
Output:
```
bundling src/main/resources/specs/co2-sensors.yml...
📦 Created a bundle for src/main/resources/specs/co2-sensors.yml at bundled.yaml 76ms.
```

### OpenAPI code generator
Maven plugin to support the OpenAPI code generator this generate java classes for the following packages

``` java
com.weather.sensors.api
com.weather.sensors.model
```

#### Usage

``` bash
mvn clean install
```

to just generate the sources without compiling or running the tests

``` bash
mvn clean generate-sources
```

# ms-co2-sensors-be

ms-co2-sensors-be is a Spring Boot application that uses:
* Java 11
* Maven
* postgres database

## Configure the ms-co2-sensors database locally
### without docker, using DB-studio like PgAdmin
Install the Postgres database locally and create a database named `co2-sensor` with username `co2-sensor_be` and password `co2-sensor_be`.

first you need to create the database and schema.
```sql
CREATE DATABASE co2-sensor;
CREATE schema co2-sensor_be;
```

then create the app user and password.
```sql
CREATE USER co2-sensor_be WITH PASSWORD 'co2-sensor_be';
```

## Run the project as Spring boot
On IDE set the profile as **local** on run configuration:
``` 
spring.profiles.active=local
```
Run the application as spring boot project.

Once Project runs successfully, goto below url on browser to access Swagger Api Documentation:

[http://localhost:8093/swagger-ui/index.html](http://localhost:8093/swagger-ui/index.html)