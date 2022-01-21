# OpenPayd Exchange API
## Properties:
**1.** Java language level: 17

**2.** Fixerio api is used as rate conversion service
(Base url is written in application.properties file.)

**3.** Fixerio accepts only "EUR" as base currency in free plan
(Appropriate message is send if any other base currency is send )

**4.** Project is running on port 8085

**5.** H2 database used for convenience: 

## How to run:
**-**  In the root directory  run `mvn verify` and then `java -jar target/openpayd-0.1.jar` 

**-**  Alternatively you can use `mvn spring-boot:run` in the root directory.

## Info:
**-** Api base url can be reached from: http://localhost:/8085

**-** H2 database runs on http://localhost:8085/h2

**-** Api documentation can be reached from: http://localhost:8085/v2/api-docs

**-** Sample request/response can be found in `openpayd.postman_collection.json` file



