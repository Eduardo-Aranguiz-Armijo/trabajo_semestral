E-Commerce Shop — Arquitectura de Microservicios

Descripción General

E-Commerce Shop es una plataforma de comercio electrónico desarrollada bajo una arquitectura basada en microservicios utilizando Spring Boot, permitiendo una estructura modular, escalable y desacoplada para la gestión completa de una tienda online.

El sistema está compuesto por múltiples servicios independientes que se comunican entre sí mediante APIs REST, permitiendo administrar de forma separada la autenticación, productos, inventario, pagos, órdenes, clientes y notificaciones del sistema.

La arquitectura implementa separación de responsabilidades, seguridad centralizada mediante JWT y comunicación desacoplada entre servicios utilizando WebClient y API Gateway.
```
Tecnologías Utilizadas

Tecnología                  Uso
Lenguaje                     Java 21
Framework Principal          Spring Boot 3.x
Seguridad                    Spring Security + JWT
Persistencia                 Spring Data JPA / MySQL
Gestión de Dependencias      Maven
Comunicación Inter-service   WebClient
Enrutamiento y Acceso        API Gateway (Spring Cloud Gateway)
Arquitectura                 Microservicios
Migraciones DB               Flyway
Boilerplate                  Lombok

```


Arquitectura del Proyecto
El sistema está dividido en los siguientes módulos y microservicios independientes:
```
Componente / Servicio      Función
gateway                      Punto de entrada único y enrutamiento inteligente para todas las peticiones externas.
auth-service                 Gestión de usuarios, registro, login y generación de tokens JWT para autenticación y autorización.
cart-service                 Gestión del carrito de compras de los usuarios, interactuando con productos e inventario.
catalog-service              Administración de categorías de productos.
comprobante                  Generación y consulta de comprobantes digitales de pago.
customer-service             Gestión de perfiles de clientes asociados a los usuarios.
inventory-service            Control de stock de los productos, permitiendo consultar y descontar inventario.
notificaciones               Envío de notificaciones a los clientes (ej. confirmación de pago, alertas).
order-service                Administración y procesamiento de órdenes, transformando un carrito en una orden pendiente.
payment-service              Procesamiento de pagos, orquestando la actualización de la orden, notificación y comprobante.
product-service              Gestión completa del catálogo de productos (CRUD).
```
Flujo General del Sistema
```
                  Cliente
                     ↓
               Gateway (5995)
                     ↓
       ┌─────────────┴─────────────┐
       ▼                           ▼
Auth Service (8081) → JWT   Product Service (8090)
                                   ↓
                           Catalog Service (8083)
                                   ↓
                            Cart Service (8082)
                                   ↓
                         Customer Service (8085)
                                   ↓
                           Order Service (8088)
                                   ↓
                          Payment Service (8089)
                                   ↓
                         Inventory Service (8086)
                                   ↓
                          Notificaciones (8087)
                                   ↓
                           Comprobante (8084)
```
```                         
Explicación del Flujo

Servicio            Puerto         Función en el flujo
Gateway              5995           Punto de entrada principal del sistema, enrutando las peticiones a los microservicios correspondientes.
Auth Service         8081           Autenticación de usuarios y generación de tokens JWT para asegurar las comunicaciones.
Product Service      8090           Gestión y consulta de productos disponibles en la tienda.
Catalog Service      8083           Administración de las categorías a las que pertenecen los productos.
Cart Service         8082           Permite a los usuarios añadir, eliminar y gestionar los productos que desean comprar.
Customer Service     8085           Almacena y gestiona la información detallada de los clientes.
Order Service        8088           Crea y procesa las órdenes de compra a partir del carrito del usuario, descontando el stock.
Payment Service      8089           Procesa los pagos, actualiza el estado de la orden, y coordina con los servicios de notificación y comprobante.
Inventory Service    8086           Actualiza el stock de productos en tiempo real tras una compra o devolución.
Notificaciones       8087           Envía correos electrónicos y otras alertas a los clientes sobre el estado de sus pedidos.
Comprobante          8084           Genera y almacena los comprobantes de las transacciones realizadas.

```


Características Principales

Seguridad

• Autenticación centralizada mediante JWT.

• Protección de endpoints mediante Spring Security.

• Validación segura de usuarios y permisos (roles ADMIN).

• Generación de tokens mediante auth-service.

Gestión de Productos

• CRUD completo de productos.

• Administración de categorías para una mejor organización.

• Comunicación desacoplada entre servicios para obtener información de productos y categorías.

Gestión de Inventario

• Verificación automática de stock al añadir productos al carrito o crear una orden.

• Actualización en tiempo real del inventario tras las transacciones.

• Integración con los servicios de órdenes y pagos.

Carrito y Ventas

• Carrito persistente por cliente.

• Administración de órdenes de compra.

• Flujo completo de compra (checkout) orquestado entre microservicios.

Notificaciones y Comprobantes

• Envío automático de alertas y confirmaciones de compra.

• Generación de comprobantes digitales para cada transacción.

Estructura del Proyecto
```
Plain Text


trabajo_semestral
├── gateway
├── auth-service
├── cart-service
├── catalog-service
├── comprobante
├── customer-service
├── inventory-service
├── notificaciones
├── order-service
├── payment-service
└── product-service
```


Configuración de Puertos
```
Servicio            Puerto
Gateway              5995
Auth Service         8081
Cart Service         8082
Catalog Service      8083
Comprobante          8084
Customer Service     8085
Inventory Service    8086
Notificaciones       8087
Order Service        8088
Payment Service      8089
Product Service      8090
```



Comunicación Entre Microservicios
El proyecto utiliza:
```
Tecnología                      Función
REST APIs                        Comunicación HTTP estándar entre servicios.
WebClient                        Cliente HTTP reactivo para el consumo de APIs entre microservicios.
DTOs (Data Transfer Objects)     Objetos para la transferencia de datos entre servicios, asegurando la consistencia.
JWT                              Mecanismo de seguridad para la autenticación y autorización de las peticiones.
API Gateway                      Centralización del acceso y enrutamiento de las peticiones a los microservicios internos.
```



Ejecución en IntelliJ IDEA

1. Requisitos Previos

• Java JDK 21 instalado y configurado.

• Maven configurado correctamente.

• MySQL ejecutándose localmente (puerto 3306 por defecto).

• Bases de datos creadas para cada microservicio (o configuradas para creación automática por Flyway).

2. Abrir Proyecto

Abrir IntelliJ IDEA y seleccionar la carpeta raíz del proyecto:

trabajo_semestral

Esperar la sincronización automática de dependencias Maven.

3. Orden de Ejecución

Es crucial ejecutar las clases @SpringBootApplication en el siguiente orden para asegurar el correcto funcionamiento de las dependencias entre servicios:

1. GatewayApplication

2. AuthServiceApplication

3. CatalogServiceApplication

4. ProductServiceApplication

5. InventoryServiceApplication

6. CustomerServiceApplication

7. CartServiceApplication

8. OrderServiceApplication

9. PaymentServiceApplication

10. NotificacionesApplication

11. ComprobanteApplication

Endpoints de Referencia (a través del Gateway)

Todas las peticiones deben dirigirse al Gateway en http://localhost:5995 seguido del prefijo /api/v1/.

Autenticación

• POST /api/v1/auth/login: Iniciar sesión y obtener token JWT.

• POST /api/v1/auth/register: Registrar un nuevo usuario.

Productos

• GET /api/v1/products: Obtener todos los productos.

• POST /api/v1/products: Crear un nuevo producto (requiere rol ADMIN ).

• PUT /api/v1/products/{id}: Actualizar un producto existente (requiere rol ADMIN).

• DELETE /api/v1/products/{id}: Eliminar un producto (requiere rol ADMIN).

Inventario

• GET /api/v1/inventory/product/{productId}: Obtener el inventario de un producto específico.

• PUT /api/v1/inventory/product/{productId}: Actualizar el stock de un producto (requiere rol ADMIN).

• PUT /api/v1/inventory/product/{productId}/decrease: Decrementar el stock de un producto (requiere rol ADMIN).

Carrito

• GET /api/v1/carrito/my-cart: Obtener el carrito del usuario autenticado.

• POST /api/v1/carrito/items: Añadir un ítem al carrito.

• DELETE /api/v1/carrito/{id}: Eliminar un carrito por ID.

Órdenes

• POST /api/v1/ordenes: Crear una nueva orden a partir del carrito.

• GET /api/v1/ordenes/{id}: Obtener una orden por ID.

Objetivo del Proyecto

El objetivo principal del proyecto es aplicar patrones modernos de arquitectura de software para backend empresarial, utilizando microservicios para resolver problemas de escalabilidad, mantenibilidad y desacoplamiento.

El sistema busca representar el flujo completo de una plataforma E-Commerce empresarial utilizando tecnologías modernas del ecosistema Spring.

Integrantes:

- Eduardo Aranguiz
- Jerson Pedreros
- Edward Cardoza



```Guia de uso de postman
1. Registro de Usuario y Cliente
Primero, creamos una cuenta. Este endpoint es especial porque registra al usuario en el sistema de autenticación y crea su perfil de cliente simultáneamente.
URL: POST http://localhost:5995/api/v1/auth/register
Body (JSON ):

{
  "username": "juan_perez",
  "password": "password123",
  "role": "USER",
  "nombre": "Juan Pérez",
  "rut": "12.345.678-9",
  "correo": "juan@example.com",
  "direccion": "Calle Falsa 123",
  "telefono": "+56912345678"
}

2. Inicio de Sesión (Obtener Token)
Para realizar cualquier otra acción, necesitas el token JWT.
URL: POST http://localhost:5995/api/v1/auth/login
Body (JSON ):

{
  "username": "juan_perez",
  "password": "password123"
}

Resultado: Copia el valor del campo "token" de la respuesta. En las siguientes peticiones, ve a la pestaña Authorization, selecciona Bearer Token y pega este código.
3. Gestionar el Carrito
Una vez autenticado, puedes añadir productos. (Asegúrate de que existan productos con ID 1 en tu base de datos).
URL: POST http://localhost:5995/api/v1/cart/items
Headers: Authorization: Bearer <tu_token>
Body (JSON ):

{
  "productId": 1,
  "cantidad": 2
}

4. Crear la Orden
Cuando estés listo para comprar, conviertes el carrito en una orden. Este endpoint no requiere un cuerpo JSON porque el sistema ya sabe quién eres por tu token.
URL: POST http://localhost:5995/api/v1/orders
Headers: Authorization: Bearer <tu_token>
Body: (Vacío )
Resultado: Copia el "id" de la orden creada (ej. 1).
5. Registrar Método de Pago
Antes de pagar, debes registrar una tarjeta asociada a tu cuenta.
URL: POST http://localhost:5995/api/v1/payment-methods
Headers: Authorization: Bearer <tu_token>
Body (JSON ):

{
  "cardHolder": "Juan Pérez",
  "cardNumber": "1234567812345678",
  "expirationDate": "12/28",
  "cvv": "123"
}

Resultado: Copia el "id" del método de pago (ej. 1).
6. Procesar el Pago (Finalizar Compra)
Este es el paso final que dispara todo el flujo: valida el stock, cambia la orden a pagada, envía la notificación y genera el comprobante.
URL: POST http://localhost:5995/api/v1/payments
Headers: Authorization: Bearer <tu_token>
Body (JSON ):

{
  "orderId": 1,
  "paymentMethodId": 1
}

```
