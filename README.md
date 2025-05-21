# Client Service - Microservicio de Gestión de Clientes

Este proyecto es un microservicio desarrollado en Java con Spring Boot, siguiendo principios de arquitectura hexagonal. Permite gestionar clientes, calcular métricas y emitir eventos asíncronos, cumpliendo con los requerimientos del reto técnico.

---

## Requisitos del Reto

1. **Funcionalidades:**
    - Registro de nuevos clientes (nombre, apellido, edad, fecha de nacimiento).
    - Listado completo de clientes incluyendo **esperanza de vida** calculada.
    - Cálculo de métricas: promedio de edad y desviación estándar.
2. **Requisitos Técnicos:**
    - Arquitectura basada en buenas prácticas (Hexagonal, DTOs, Repository, etc.).
    - Seguridad con JWT.
    - Documentación Swagger.
    - Persistencia con PostgreSQL.
    - Mensajería asíncrona con RabbitMQ.
    - Observabilidad con Spring Boot Actuator.
    - Preparado para despliegue en la nube.
3. **Extras implementados:**
    - Exposición de métricas `/actuator/prometheus`
    - Logs estructurados

---

## Tecnologías Usadas

- Java 17
- Spring Boot 3.2
- PostgreSQL
- RabbitMQ
- Spring Security (JWT)
- SpringDoc OpenAPI
- Spring Actuator
- JUnit, Mockito
- Jacoco (cobertura de pruebas)

---

## Autenticación

```
POST /auth/login

```

**Request:**

```json
{
  "username": "admin",
  "password": "password"
}

```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}

```

---

## Crear cliente

```
POST /clients

```

**Headers:**

```
Authorization: Bearer {tu_token}
Content-Type: application/json

```

**Request body (JSON):**

```json
{
  "firstName": "María",
  "lastName": "González",
  "age": 35,
  "birthDate": "1989-04-10"
}

```

**Response:**

```json
{
  "firstName": "María",
  "lastName": "González",
  "age": 35,
  "birthDate": "1989-04-10",
  "estimatedDeathDate": "2069-04-10"
}

```

---

## Listar todos los clientes

```
GET /clients

```

Devuelve lista completa de clientes con sus datos y **esperanza de vida** estimada (campo `estimatedDeathDate`).

---

## Métricas de edad

```
GET /clients/metrics

```

**Response:**

```json
{
  "averageAge": 33.2,
  "standardDeviation": 4.1
}

```

---

## Mensajería (RabbitMQ)

Cada vez que se crea un cliente, se envía un evento asíncrono con esta estructura:

```json
{
  "eventType": "CLIENT_CREATED",
  "firstName": "María",
  "lastName": "González",
  "age": 35,
  "birthDate": "1989-04-10"
}

```

### URLs locales:

- RabbitMQ Dashboard: [http://localhost:15672](http://localhost:15672/) (user/pass: `guest` / `guest`)
- Cola configurada: `client.queue` (ver `application.yml`)

---

## Swagger UI

```
http://localhost:8080/swagger-ui/index.html

```

Explora y prueba todos los endpoints desde la interfaz web.

---

## Prometheus / Actuator

```
http://localhost:8080/actuator/prometheus

```

Exposición de métricas en formato Prometheus.

---

## Ejecución del Proyecto

1. Clona el repositorio:

```
git clone https://github.com/tu_usuario/client-service.git

```

## Docker

1. Ejecuta el proyecto con Docker Compose:

```xml
docker-compose up --build
```

1. RabbitMQ debe estar corriendo (usar Docker):

```
docker run -d -p 5672:5672 -p 15672:15672 rabbitmq:3-management

```

1. PostgreSQL debe estar configurado en `application.yml`.

---

## Pruebas

```
./gradlew test
./gradlew jacocoTestReport

```

Reporte HTML de cobertura en: `build/jacocoHtml/index.html`

---

## Decisiones arquitectónicas

- Arquitectura hexagonal (puertos y adaptadores)
- Eventos asincrónicos con RabbitMQ
- Seguridad JWT manual sin dependencia de OAuth
- OpenAPI documentado con SpringDoc
- Métricas expuestas vía Actuator
- Logs en consola e integración lista para monitorización

---

## Estado del reto

| Requisito | Estado |
| --- | --- |
| Crear clientes | ✅ |
| Consultar métricas | ✅ |
| Listar clientes con cálculo | ✅ |
| Documentación Swagger | ✅ |
| Persistencia con PostgreSQL | ✅ |
| Seguridad JWT | ✅ |
| Mensajería asíncrona | ✅ |
| Logs y Actuator | ✅ |
| Preparado para la nube | ✅ |
| Extra Prometheus | ✅ |