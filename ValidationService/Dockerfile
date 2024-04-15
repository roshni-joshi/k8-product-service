FROM openjdk:17
EXPOSE 80
ADD target/validation-service.jar validation-service.jar
ENTRYPOINT ["java", "-jar", "validation-service.jar"]