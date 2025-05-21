FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine AS builder

# Instalar Gradle manualmente
ENV GRADLE_VERSION=8.5
RUN wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle-${GRADLE_VERSION}-bin.zip -d /opt \
    && rm gradle-${GRADLE_VERSION}-bin.zip
ENV PATH=$PATH:/opt/gradle-${GRADLE_VERSION}/bin

WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# Etapa de ejecución
FROM --platform=linux/amd64 eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]