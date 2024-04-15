FROM openjdk:17
EXPOSE 7070
ADD target/calculation-service.jar calculation-service.jar
ENTRYPOINT ["java", "-jar", "calculation-service.jar"]