FROM openjdk:17

WORKDIR /app/src

EXPOSE 8080 8080

COPY .  /app/src/

RUN sh mvnw clean package -D skipTests

CMD [ "java", "-jar", "target/phones-0.0.1-SNAPSHOT.jar" ]