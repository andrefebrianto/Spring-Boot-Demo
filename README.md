# Spring-Boot-Demo
This README would normally document whatever steps are necessary to get your application up and running.

### Requirements ###
* Java environment setup (java 18)
* IDE setup (Download Intellij Idea)

### Using the formatter ###
* Use spotless to enforce the Java Google Code format
* To apply spotless run:`mvn spotless:apply`

### Log file ###
* Log file can be found at`./logs`

### Database migration ###
* Migration script can be found at`./src/main/resources/db/migration`
* Use`v<timestamp>__<description>.sql`naming convention for migration scripts

### API document ###
* Swagger UI document url http://localhost:8080/swagger-ui/index.html
