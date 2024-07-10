FROM openjdk:17

WORKDIR /app

COPY target/*.jar productcatalog.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "productcatalog.jar"]
