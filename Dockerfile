# ---------- build stage ----------
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests clean package

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre

WORKDIR /app

# ОБЯЗАТЕЛЬНО проверь, что имя jar совпадает с artifactId+version из pom.xml
ARG JAR_FILE=target/catalog-service-0.0.1-SNAPSHOT.jar
COPY --from=build /app/${JAR_FILE} app.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","/app/app.jar"]
