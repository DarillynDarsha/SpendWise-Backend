# =====================================
# SPENDWISE - BACKEND DOCKER CONFIG
# Java 21 + Spring Boot + Maven
# =====================================

# Build Stage
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven project files
COPY . .

# Build Spring Boot application
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# =====================================
# Runtime Stage
# =====================================

FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy generated JAR
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Start Spring Boot application
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]