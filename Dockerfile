FROM openjdk:8
ADD target/exchange-service.jar exchange-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "exchange-service.jar"]