# 🛒 E-Commerce Microservices

> Sistema de comercio electrónico desarrollado con **Java 17** y **Spring Boot**, implementando una arquitectura basada en microservicios utilizando **Spring Cloud**. El proyecto demuestra una solución escalable, modular y desacoplada para la gestión de un sistema de ventas en línea mediante autenticación JWT, descubrimiento de servicios con Eureka Server, API Gateway y bases de datos independientes para cada microservicio.

---

# 📖 Descripción

Este proyecto implementa una arquitectura de microservicios donde cada servicio es completamente independiente y posee su propia base de datos.

La comunicación entre los distintos servicios se realiza mediante Spring Cloud, mientras que **Eureka Server** permite el descubrimiento automático de servicios y **API Gateway** actúa como punto único de entrada para todas las peticiones del cliente.

El sistema contempla la administración completa de un comercio electrónico, incluyendo autenticación, gestión de productos, categorías, inventario, clientes, carrito de compras, pedidos, pagos, comprobantes y notificaciones.

---

# 🚀 Tecnologías Utilizadas

```text
• Java 17
• Spring Boot 3
• Spring Cloud
• Spring Cloud Gateway
• Netflix Eureka Server
• Spring Security
• JWT (JSON Web Token)
• Spring Data JPA
• Hibernate
• MySQL
• Maven
• Docker
• Docker Compose
• OpenAPI / Swagger
```

---

# 🏛 Arquitectura

```text
                              Cliente
                                 │
                                 ▼
                      API Gateway (9090)
                                 │
                                 ▼
                        Eureka Server (8761)
                                 │
 ┌──────────────────────────────────────────────────────────────┐
 │                                                              │
 ▼                                                              ▼
Auth Service                                             Product Service
     │                                                          │
     ▼                                                          ▼
Customer Service                                         Catalog Service
     │                                                          │
     ▼                                                          ▼
Cart Service                                            Inventory Service
     │
     ▼
Order Service
     │
     ├───────────────────────┐
     ▼                       ▼
Payment Service       Comprobante Service
                              │
                              ▼
                    Notificaciones Service
```

Cada microservicio:

* Posee su propia base de datos.
* Se registra automáticamente en Eureka.
* Puede ejecutarse de manera independiente.
* Se comunica mediante Spring Cloud.
* Mantiene una arquitectura desacoplada.

---

# 📂 Microservicios

```text
┌──────────────────────────┬────────┐
│ Servicio                 │ Puerto │
├──────────────────────────┼────────┤
│ Eureka Server            │ 8761   │
│ API Gateway              │ 9090   │
│ Auth Service             │ 8082   │
│ Cart Service             │ 8083   │
│ Inventory Service        │ 8084   │
│ Notificaciones Service   │ 8085   │
│ Comprobante Service      │ 8086   │
│ Payment Service          │ 8087   │
│ Order Service            │ 8088   │
│ Customer Service         │ 8089   │
│ Catalog Service          │ 8090   │
│ Product Service          │ 8091   │
└──────────────────────────┴────────┘
```

---

# ⚙ Funcionalidades

## 🔐 Autenticación

* Registro de usuarios.
* Inicio de sesión.
* Generación de JWT.
* Validación de Token.
* Protección de Endpoints mediante Spring Security.

---

## 📦 Gestión de Productos

* Crear productos.
* Actualizar productos.
* Eliminar productos.
* Consultar productos.

---

## 📁 Gestión de Categorías

* Crear categorías.
* Modificar categorías.
* Eliminar categorías.
* Consultar categorías.

---

## 📊 Inventario

* Administración de stock.
* Actualización automática.
* Validación de disponibilidad de productos.

---

## 🛒 Carrito de Compras

* Agregar productos.
* Eliminar productos.
* Actualizar cantidades.
* Consultar carrito.

---

## 👤 Clientes

* Registro de clientes.
* Gestión de información.
* Administración de datos personales.

---

## 💳 Pagos

* Registro de pagos.
* Confirmación de transacciones.
* Validación del proceso de pago.

---

## 📄 Comprobantes

* Generación automática.
* Registro de comprobantes.
* Asociación con pedidos realizados.

---

## 🔔 Notificaciones

* Confirmación de compras.
* Avisos del sistema.
* Comunicación entre servicios.

---

# 🗄 Bases de Datos

Crear previamente las siguientes bases de datos en MySQL.

```sql
CREATE DATABASE db_auth;
CREATE DATABASE productos_db;
CREATE DATABASE carrito_db;
CREATE DATABASE catalogos_db;
CREATE DATABASE customer;
CREATE DATABASE inventory_db;
CREATE DATABASE payment_db;
CREATE DATABASE orden_db;
CREATE DATABASE comprobante_db;
CREATE DATABASE notificaciones_db;
```
---

# 📁 Estructura del Proyecto

```text
📦 E-Commerce-Microservices
│
├── api-gateway
├── auth-service
├── cart-service
├── catalog-service
├── comprobante
├── customer-service
├── eureka-server
├── inventory-service
├── notificaciones
├── order-service
├── payment-service
├── product-service
│
├── docker-compose.yml
├── README.md
└── pom.xml
```

---

# 🔄 Flujo de la Arquitectura

```text
                 Cliente
                     │
                     ▼
           API Gateway (9090)
                     │
                     ▼
            Eureka Server (8761)
                     │
     ┌───────────────┼────────────────┐
     │               │                │
     ▼               ▼                ▼
 Auth Service   Product Service   Customer Service
     │               │                │
     ▼               ▼                ▼
 Cart Service  Catalog Service  Inventory Service
                     │
                     ▼
              Order Service
                     │
          ┌──────────┴──────────┐
          ▼                     ▼
  Payment Service      Comprobante Service
                                │
                                ▼
                    Notificaciones Service
```

---

# ▶ Instalación

## 1. Clonar el repositorio

```bash
git clone https://github.com/Eduardo-Aranguiz-Armijo/trabajo_semestral.git
```

---

## 2. Acceder al proyecto

```bash
cd trabajo_semestral
```

---

## 3. Configurar MySQL

Editar los archivos **application.properties** de cada microservicio configurando:

* Usuario
* Contraseña
* Puerto de MySQL
* Nombre de la base de datos correspondiente

---

## 4. Ejecutar los servicios

Se recomienda iniciar los microservicios en el siguiente orden:

```text
1. Eureka Server
2. API Gateway
3. Auth Service
4. Product Service
5. Catalog Service
6. Inventory Service
7. Cart Service
8. Customer Service
9. Payment Service
10. Order Service
11. Comprobante Service
12. Notificaciones Service
```

Una vez iniciados todos los servicios, el sistema estará disponible para recibir solicitudes a través del API Gateway.

---

# 🐳 Docker

El proyecto incorpora archivos **Dockerfile** y un archivo **docker-compose.yml**, permitiendo desplegar toda la arquitectura mediante contenedores Docker.

Para construir y ejecutar todos los servicios:

```bash
docker compose up --build
```

Para detener todos los contenedores:

```bash
docker compose down
```

---

# 🔒 Seguridad

El sistema implementa múltiples mecanismos de seguridad para proteger los recursos expuestos por los microservicios.

Características implementadas:

* Spring Security.
* JWT (JSON Web Token).
* Protección de endpoints.
* Autenticación basada en Tokens.
* Validación de usuarios.
* Control de acceso mediante filtros de seguridad.

---

# 🌐 Comunicación entre Microservicios

La arquitectura utiliza Spring Cloud para facilitar la comunicación entre servicios.

Características principales:

* Registro automático mediante Eureka Server.
* Descubrimiento dinámico de servicios.
* API Gateway como punto único de acceso.
* Comunicación desacoplada entre microservicios.
* Bases de datos independientes para cada servicio.

---

# ⭐ Características de la Arquitectura

* Arquitectura basada en Microservicios.
* Separación de responsabilidades.
* Alta escalabilidad.
* Fácil mantenimiento.
* Despliegue independiente de cada servicio.
* Descubrimiento automático mediante Eureka.
* Seguridad mediante JWT.
* Persistencia independiente por servicio.
* Integración mediante Spring Cloud.
* Preparado para contenerización con Docker.

---

# 📖 Documentación de la API

El proyecto incorpora soporte para **OpenAPI / Swagger**, facilitando la documentación y prueba de los endpoints REST de los distintos microservicios cuando se encuentran habilitados.

---

# 🎯 Objetivos del Proyecto

Este proyecto fue desarrollado con fines académicos para demostrar la implementación de una arquitectura moderna basada en microservicios utilizando el ecosistema Spring.

Entre sus principales objetivos destacan:

* Implementar una arquitectura desacoplada.
* Aplicar buenas prácticas de desarrollo.
* Utilizar autenticación segura mediante JWT.
* Gestionar múltiples bases de datos independientes.
* Integrar servicios mediante Spring Cloud.
* Facilitar el despliegue utilizando Docker.

---

# 👨‍💻 Autores

**Eduardo Aranguiz Armijo**

**Jerson Pedreros**

**Edward Cardoza Mesina**

