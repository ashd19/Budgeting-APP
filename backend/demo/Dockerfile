# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies (cached layer)
RUN chmod +x mvnw && ./mvnw dependency:resolve -B

# Copy source code and build
COPY src/ src/
RUN ./mvnw package -DskipTests -B

# Stage 2: Run the application
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
