FROM bellsoft/liberica-openjdk-debian:17

WORKDIR /app

COPY target/service_discovery-0.0.1-SNAPSHOT.jar /app/service_discovery-0.0.1-SNAPSHOT.jar

CMD  [ "java", "-jar", "service_discovery-0.0.1-SNAPSHOT.jar" ]

EXPOSE 0