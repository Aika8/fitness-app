FROM openjdk:15-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

EXPOSE 80

CMD ["./mvnw", "spring-boot:run"]