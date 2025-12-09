# Etapa 1: Construcción (Build)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Run)
# --- CAMBIO IMPORTANTE AQUÍ ABAJO ---
# Usamos eclipse-temurin porque openjdk:17-jdk-slim ya no funciona
FROM eclipse-temurin:17-jdk-jammy
# ------------------------------------

# Asegúrate de que este nombre coincida con tu pom.xml (Gaestion...)
COPY --from=build /app/target/GaestionEventosEscom-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]