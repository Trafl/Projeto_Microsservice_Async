FROM bellsoft/liberica-openjdk-debian:17

WORKDIR /app

COPY target/service_notificacao-0.0.1-SNAPSHOT.jar /app/service_notificacao-0.0.1-SNAPSHOT.jar

CMD  [ "java", "-jar", "service_notificacao-0.0.1-SNAPSHOT.jar" ]

EXPOSE 0