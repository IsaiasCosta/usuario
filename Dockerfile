
FROM gradle:7.6.4-jdk21  AS BUILD

WORKDIR /app

COPY . .
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon


FROM openjdk:21

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/usuario.jar

EXPOSE 8080

CMD ["java","-jar","/app/usuario.jar"]
