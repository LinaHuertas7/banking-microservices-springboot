FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

EXPOSE 8082

CMD ["mvn", "compile", "spring-boot:run", "-Dspring-boot.run.fork=false"]