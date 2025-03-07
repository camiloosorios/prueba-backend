# API de Gestión de Pedidos

Esta API permite gestionar pedidos, productos, usuarios y reservas de inventario en un sistema de compras en línea.

## Tecnologías
- **Java 17**
- **Spring Boot 3.4.3**
- **Spring Security con JWT**
- **Base de datos PostgreSQL**
- **Docker y Docker Compose**
- **Kafka**

## Configuración con Docker

La aplicación se puede ejecutar con Docker utilizando el archivo `docker-compose.yml`, el cual define las variables de entorno necesarias para la configuración de la API.

### Ejecución

```sh
docker-compose up --build
```

Esto levantará la aplicación junto con la base de datos PostgreSQL.

## Endpoints

### Autenticación
- `POST /auth/login`: Iniciar sesión y obtener un token JWT.
- `POST /auth/register`: Registrar un nuevo usuario.

### Gestión de usuarios
- `GET /api/users/{id}`: Obtener detalles de un usuario por su ID.

### Gestión de pedidos
- `GET /orders`: Listar todos los pedidos.
- `POST /orders`: Crear un nuevo pedido.
- `GET /orders/{id}`: Obtener detalles de un pedido específico.
- `PUT /orders/{id}`: Actualizar un pedido existente.
- `DELETE /orders/{id}`: Eliminar un pedido.

### Gestión de productos
- `GET /products`: Listar todos los productos.
- `POST /products`: Agregar un nuevo producto.
- `GET /products/{id}`: Obtener detalles de un producto.
- `PUT /products/{id}`: Actualizar un producto existente.
- `DELETE /products/{id}`: Eliminar un producto.

### Gestión de reservas
- `POST /api/reservations`: Crear una nueva reserva.
- `GET /api/reservations/{id}`: Obtener detalles de una reserva por su ID.
- `PUT /api/reservations/{id}`: Actualizar el estado de una reserva.
- `DELETE /api/reservations/{id}`: Eliminar una reserva.
- `GET /api/reservations/order/{orderId}`: Obtener todas las reservas asociadas a un pedido por su ID.
- `GET /api/reservations/order/{orderId}/product/{productId}`: Obtener detalles de las reservas asociadas a un pedido y producto específicos.

## Seguridad
Para acceder a los endpoints protegidos, se debe incluir un token de autorización válido en el encabezado de las peticiones.

```
Authorization: Bearer <token>
```

## Documentación con Swagger

La documentación de la API está disponible en Swagger UI en el siguiente endpoint:

`
GET /swagger-ui.html
`

Esta URL permite visualizar y probar los endpoints de la API de manera interactiva.

