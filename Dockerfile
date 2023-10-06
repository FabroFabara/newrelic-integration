# Usar imagen base de Java 11
FROM openjdk:11-jdk-slim

LABEL maintainer="fabriciofabara@gmail.com"

VOLUME /tmp

# Copiar el JAR de la aplicación al contenedor
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Crear directorio para New Relic y copiar el agente y su configuración
RUN mkdir -p /usr/local/tomcat/newrelic
COPY ./newrelic/newrelic.jar /usr/local/tomcat/newrelic/newrelic.jar
COPY ./newrelic/newrelic.yml /usr/local/tomcat/newrelic/newrelic.yml

# Establecer opciones de Java para New Relic
ENV JAVA_OPTS="$JAVA_OPTS -javaagent:/usr/local/tomcat/newrelic/newrelic.jar"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
