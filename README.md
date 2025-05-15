# 💸 Transfers API

Este proyecto es una API para gestionar transferencias bancarias entre cuentas, desarrollada en **Java 17** con **Spring Boot**, usando **RabbitMQ** para mensajería y **SQL Server** como base de datos. Toda la aplicación está dockerizada para facilitar su despliegue.

---
# ✅ Requisitos Previos
>[!IMPORTANT]
Antes de ejecutar este proyecto, asegúrate de tener instalado lo siguiente en tu máquina:

- **Docker Desktop**: Necesario para levantar los contenedores de la aplicación, base de datos y RabbitMQ. Puedes descargarlo desde aquí:
>[!TIP]
>👉 [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)

  Asegúrate de que Docker esté corriendo antes de continuar. Puedes verificarlo ejecutando:

## 🚀 Tecnologías utilizadas
>[!NOTE]
>
- Java 17
- Spring Boot
- Maven
- RabbitMQ
- SQL Server
- Docker & Docker Compose
- Swagger UI

---

## 📦 Clonar y ejecutar el proyecto

Sigue estos pasos para compilar y ejecutar la aplicación de forma local:

### 1. Clona este repositorio

bash
git clone https://github.com/BorisMejia/transfers-bank.git
cd transfers-bank

### 2. Compila el proyecto para generar el .jar

mvn clean package -DskipTests

### 3. levantar los contenedores

docker-compose up --build

### 4. Crear la base de datos bankFam

Se ejecutan solo dos contenedores la base de datos y rabbitMQ.
Se debera crear la base de datos con nombre bankFam y se debera ejecutar el contenedor app-transfers

### 5. Probar los endpoint

Esta url sera por donde se realicen las pruebas de los diferentes endpoint
http://localhost:8080/api/swagger-ui.html
