FROM adoptopenjdk/openjdk11-openj9 AS base
COPY target/account-*.jar /app.jar

EXPOSE 8090

CMD java -jar /app.jar