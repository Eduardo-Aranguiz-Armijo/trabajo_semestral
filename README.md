# E-Commerce Shop — Arquitectura de Microservicios

## Descripción General
**E-Commerce Shop** es una plataforma de comercio desarrollada bajo una arquitectura basada en microservicios utilizando **Spring Boot**, permitiendo una estructura modular, escalable y desacoplada para la gestión completa de una tienda online.

El sistema está compuesto por múltiples servicios independientes que se comunican entre sí mediante APIs REST para administrar de forma separada todo el flujo de la tienda.

---

## Tecnologías Utilizadas
* **Lenguaje:** Java 17
* **Framework Principal:** Spring Boot
* **Seguridad:** Spring Security + JWT
* **Persistencia:** Spring Data JPA / MySQL
* **Gestión de Dependencias:** Maven
* **Comunicación Inter-service:** WebClient
* **Enrutamiento y Acceso:** API Gateway

---

## Arquitectura del Proyecto
El sistema está dividido en los siguientes módulos y microservicios independientes:

| Componente / Servicio | Función |
| :--- | :--- |
| **gateway** | Punto de entrada único y enrutamiento inteligente de peticiones |
| **auth-service** | Autenticación, seguridad y generación de tokens JWT |
| **customer-service** | Gestión de clientes y perfiles de usuario |
| **catalog-service** | Administración de categorías del e-commerce |
| **product-service** | Gestión integral del catálogo de productos |
| **inventory-service** | Control de stock y existencias en tiempo real |
| **cart-service** | Gestión del carrito de compras por usuario |
| **order-service** | Generación, procesamiento y administración de órdenes de compra |
| **payment-service** | Procesamiento seguro de pagos |
| **notificaciones** | Servicio encargado del envío de correos y alertas al cliente |
| **comprobante** | Generación de comprobantes y documentos de la compra |

---

## Flujo General del Sistema
```text
                  Cliente
                     ↓
               Gateway (8080)
                     ↓
       ┌─────────────┴─────────────┐
       ▼                           ▼
Auth Service → JWT        Product Service
                                   ↓
                              Cart Service
                                   ↓
                             Order Service
                                   ↓
                            Payment Service
                                   ↓
                           Inventory Service
                                   ↓
                             Notificaciones
                                   ↓
                              Comprobante
Características Principales
- Seguridad
Autenticación centralizada mediante tokens JWT.

Protección de endpoints mediante Spring Security.

- Gestión de Productos e Inventario
CRUD completo de productos y categorías de forma independiente.

Verificación y actualización automática de stock al confirmar transacciones.

- Ventas y Notificaciones
Carrito de compras persistente por cliente.

Orquestación de órdenes de compra y procesamiento de pagos.

Emisión automatizada de comprobantes mediante el servicio de comprobante.

Despacho de alertas por el servicio de notificaciones.
trabajo_semestral
│
├── gateway
├── auth-service
├── cart-service
├── catalog-service
├── customer-service
├── product-service
├── inventory-service
├── order-service
├── payment-service
├── notificaciones
└── comprobante
Configuración de Puertos
Servicio          Puerto
Gateway           8080
Auth Service      8081
Customer Service  8082
Catalog Service   8083
Product Service   8084
Inventory Service 8085
Cart Service      8086
Order Service     8087
Payment Service   8088
Notificaciones    8089
Comprobante       8090

Ejecución en IntelliJ IDEA
1. Requisitos Previos
Asegurarse de tener instalado Java 17 configurado en el SDK del IDE.

Tener corriendo el servidor de MySQL local con las bases de datos correspondientes para cada microservicio.

2. Abrir el Proyecto
Abrir IntelliJ IDEA, seleccionar Open (Abrir) y cargar la carpeta raíz del proyecto (trabajo_semestral).

Esperar a que Maven descargue y sincronice todas las dependencias de los submódulos de forma automática.

3. Orden de Encendido de los Servicios
Para garantizar que la comunicación y el enrutamiento a través de la pasarela funcionen correctamente,
ejecute las clases principales (@SpringBootApplication) en este orden exacto dentro de IntelliJ:

GatewayApplication (Módulo gateway)

AuthServiceApplication (Módulo auth-service)

Todos los demás servicios de negocio según se requiera (product-service, cart-service, order-service, etc.).

Endpoints de Referencia
Autenticación
POST /auth/login

POST /auth/register

Productos e Inventario
GET /api/products

POST /api/products

Carrito y Órdenes
GET /api/cart/{userId}

POST /api/cart

POST /api/orders

Objetivo del Proyecto

El objetivo principal de este proyecto es aplicar patrones avanzados de arquitectura de software
para elbackend empresarial,resolviendoproblemas de escalabilidad y desacoplamiento mediante microservicios.

Integrantes 
Eduardo Aranguiz
Jerson Pedreros
Edward Cardoza
