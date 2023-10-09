#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#WORKDIR .
#COPY /build/libs/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#ENV DATABASE_URL jdbc:postgresql://dpg-ck3koemru70s73eq88s0-a.oregon-postgres.render.com:5432/softpeach
#ENV DATABASE_USERNAME softpeach_user
#ENV DATABASE_PASSWORD tqWCRnX4ZKtTFcfq3onad9ArDis1BLPp
#EXPOSE 8080


FROM openjdk:20
WORKDIR /app
COPY build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]