FROM openjdk:15-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

EXPOSE 6969

CMD ["./mvnw", "spring-boot:run"]