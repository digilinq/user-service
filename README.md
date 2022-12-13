# User microservice

## Run Application 

```
java -jar -Dspring.profiles.active=local web/target/users-service.jar

mvn spring-boot:run -Dspring-boot.run.profiles=local
```




### Test Repository
[Database Rider](https://github.com/database-rider/database-rider)

Test the class UsersResourceIntegrationTest

`mvn test -Dtest=UsersResourceIntegrationTest -DfailIfNoTests=false`
