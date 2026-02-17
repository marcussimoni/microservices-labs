FROM bellsoft/liberica-openjdk-alpine-musl:21.0.8 AS builder

WORKDIR builder

COPY target/*.jar app.jar
COPY --from=base-image:latest /opentelemetry-javaagent.jar opentelemetry-javaagent.jar 

RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

FROM bellsoft/liberica-openjdk-alpine-musl:21.0.8

WORKDIR /app

COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./
COPY --from=builder /builder/opentelemetry-javaagent.jar/ ./opentelemetry-javaagent.jar

EXPOSE 8080

ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-Dotel.instrumentation.micrometer.enabled=true", "-Xms128m", "-Xmx256m", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=200", "-XX:+HeapDumpOnOutOfMemoryError", "-jar", "app.jar"]
