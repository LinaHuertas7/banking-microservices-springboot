# Banking Microservices — Spring Boot

Aplicación bancaria con arquitectura de microservicios para la gestión de clientes, cuentas y movimientos financieros. Backend en Java con Spring Boot, comunicación asincrónica mediante RabbitMQ y desplegado con Docker

## Prerrequisitos

- Docker y Docker Compose
- Credenciales de base de datos (MySQL y PostgreSQL)
- JDK 21 (solo para desarrollo local sin Docker)

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/LinaHuertas7/banking-microservices-springboot.git
cd banking-microservices-springboot
```

### 2. Configurar variables de entorno

Crear el archivo `.env` desde el ejemplo:

```bash
cp .env.example .env
```

Editar `.env` y completar las variables requeridas:

- Credenciales de MySQL (`db-clients`)
- Credenciales de PostgreSQL (`db-accounts`)
- Credenciales de RabbitMQ

### 3. Construir los contenedores

```bash
docker compose build
```

### 4. Levantar los servicios

```bash
docker compose up -d
```

Verificar que todos los servicios estén corriendo:

```bash
docker compose ps
```

La aplicación estará disponible en:

- **ms-clients API:** `http://localhost:8082/api`
- **ms-accounts API:** `http://localhost:8081/api`
- **Gateway Nginx:** `http://localhost:80/api`
- **RabbitMQ Panel:** `http://localhost:15672`

## Servicios

| Servicio | Puerto | Descripción |
|---|---|---|
| `ms-clients` | 8082 | API de clientes y personas |
| `ms-accounts` | 8081 | API de cuentas, movimientos y reportes |
| `db-clients` | 3306 | MySQL 8 |
| `db-accounts` | 5432 | PostgreSQL 15 |
| `rabbitmq` | 5672 / 15672 | Mensajería asincrónica |
| `nginx` | 80 | Unifica APIs |

## Estructura del proyecto

```
.
├── ms-clients/           # Microservicio de clientes
├── ms-accounts/          # Microservicio de cuentas, movimientos y reportes
├── nginx/                
├── docker-compose.yml
├── .env.example
└── README.md
```

## Endpoints disponibles

Todos los endpoints se consumen a través del gateway en `http://localhost/api`.

**Clientes**
```
POST   /api/clientes
GET    /api/clientes
GET    /api/clientes/{slug}
PUT    /api/clientes/{slug}
PATCH  /api/clientes/{slug}
DELETE /api/clientes/{slug}
```

**Cuentas**
```
POST   /api/cuentas
GET    /api/cuentas
GET    /api/cuentas/{slug}
PUT    /api/cuentas/{slug}
PATCH  /api/cuentas/{slug}
DELETE /api/cuentas/{slug}
```

**Movimientos**
```
POST   /api/movimientos
GET    /api/movimientos
GET    /api/movimientos/{slug}
DELETE /api/movimientos/{slug}
```

Los valores positivos se registran como `DEPOSITO` y los negativos como `RETIRO`. Si el saldo es insuficiente, el servicio retorna `400 - Saldo no disponible`.

**Reporte de estado de cuenta**
```
GET /api/reportes/{clienteId}?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD
```

## Comandos útiles

**Detener los servicios**
```bash
docker compose down
```

**Ver logs de un servicio**
```bash
docker compose logs -f ms-clients
docker compose logs -f ms-accounts
```

**Reiniciar un servicio**
```bash
docker compose restart ms-clients
```

## Pruebas

```bash
# ms-clients
cd ms-clients
./mvnw test

# ms-accounts
cd ms-accounts
./mvnw test

# Ejecutar una clase específica
./mvnw test -Dtest=ClientDomainTest
```

## Solución de problemas

**Los contenedores no inician**

Verificar que los puertos 3306, 5432, 5672 y 8081/8082 no estén en uso:

```bash
docker compose ps
docker compose logs
```

**Error de conexión a la base de datos**

Confirmar que las credenciales en `.env` coincidan con las del servicio correspondiente y que el contenedor de base de datos esté en estado `healthy` antes de que arranque el microservicio.

**Error de conexión a RabbitMQ**

Los microservicios esperan que RabbitMQ esté `healthy` antes de iniciar. Si el servicio reinicia constantemente, esperar unos segundos y ejecutar:

```bash
docker compose restart ms-clients ms-accounts
```