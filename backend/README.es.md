# Y - Backend API

Aquí encontrarás la API del proyecto e información referente a ella.

Al final de este documento también encontrarás las sentencias de creación de la base de datos, en caso de que quieras hacer uso de algún cliente sin tener que instalar el cliente web.

## Idiomas

- 🇬🇧 English → [README.md](README.md)
- 🇪🇸 Español 

## Responsabilidades

- Autenticación (log-in/registro)
- Gestión de posts (crear/borrar)
- Generación del Feed (global/siguiendo)
- Notificaciones 
- Gestión de seguidores

## Tecnologias

- Java
- Jakarta REST (JAX-RS)
- Apache Tomcat
- MariaDB / MySQL
- JDBC

## Arquitectura
```
apirest/                ← Raiz del proyecto
 ├ config/              ← Configuración globar y utilidades
 │    ├ RestConfig      ← Configuración de la url base de la API
 │    └ ConnectionManager.java     ← Apertura y cierre de la conexión con la base de datos
 │
 ├ resources/                   ←  clases REST (endpoints HTTP)
 │    ├ Users.java
 │    ├ Posts.java
 │    ├ Followers.java
 │    └ Notifications.java
 │
 ├ models/                      ← Entidades de las tablas
 │    ├ User.java
 │    ├ Post.java
 │    ├ Follower.java
 │    └ Notification.java
 │
 └ dto/                         ← Objetos de transferencia de datos(DTO)
      ├ LoginRequest.java
      ├ RegisterRequest.java
      └ UserUpdate.java
```

## Endpoints principales

### Usuarios
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST   | /users/register | Registrar un nuevo usuario |
| POST   | /users/login    | Autenticación de usuario |
| GET    | /users/{id}     | Obtener información de un usuario |
| PUT    | /users/update/{id} | Actualizar información de usuario |
| DELETE | /users/delete/{id} | Eliminar un usuario |

---

### Publicaciones
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET    | /posts               | Obtener todas las publicaciones (feed global) |
| GET    | /posts/{id_user}     | Obtener publicaciones de un usuario |
| GET    | /posts/following/{id_user} | Obtener publicaciones de usuarios seguidos |
| POST   | /posts/create        | Crear una nueva publicación |
| DELETE | /posts/delete/{id}   | Eliminar una publicación |

---

### Notificaciones
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /notifications/{id_user} | Obtener notificaciones de un usuario |

---

### Seguidores
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST   | /followers/{id_follower}/follow/{id_following}   | Seguir a un usuario |
| DELETE | /followers/{id_follower}/unfollow/{id_following} | Dejar de seguir a un usuario |
| GET    | /followers/isFollowing/{id_follower}/{id_following} | Verificar seguimiento entre dos usuarios |
| GET    | /followers/{id}/followersCount | Número de seguidores |
| GET    | /followers/{id}/followingCount | Número de usuarios seguidos |


## Clases auxiliares

Para facilitar la gestión de los datos enviados a la API y no enviar y recibir datos innecesarios, se crearon las siguientes clases(DTOs):

### LoginRequest
Representa los datos necesarios para el inicio de sesión de un usuario.
- **Campos:** 
  - `email` (String): Correo electrónico del usuario
  - `password` (String): Contraseña del usuario

### RegisterRequest
Representa los datos necesarios para registrar un nuevo usuario.
- **Campos:** 
  - `username` (String): Nombre de usuario deseado
  - `email` (String): Correo electrónico del usuario
  - `password` (String): Contraseña del usuario

### UserUpdate
Representa los datos necesarios para actualizar la información de un usuario.
- **Campos:** 
  - `username` (String): Nuevo nombre de usuario (opcional)
  - `description` (String): Nueva descripción (opcional)


## Guía de instalación

### Requisitos
- Java 17+
- Apache Tomcat
- MySQL / MariaDB

### Pasos
1. Clonar el repositorio
2. Crear la base de datos usando las migraciones del proyecto laravel o las sentencias SQL
3. Configurar la conexión en `ConnectionManager.java`
4. Desplegar el proyecto en Tomcat
5. Acceder en: http://localhost:8080/yapi/rest (la url puede variar en función del cliente)

### Comprobación de funcionamiento 
http://localhost:8080/yapi/rest/users/ping

Si devuelve "pong" funciona correctamente.

## Sentencias de Creación

### Sentencia de creación de la base de datos

CREATE DATABASE `y_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */

### Sentencias de creación de las tablas

CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `bio` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_username_unique` (`username`),
  UNIQUE KEY `users_email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE `posts` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) unsigned NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `posts_id_user_foreign` (`id_user`),
  CONSTRAINT `posts_id_user_foreign` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE `followers` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_follower` bigint(20) unsigned NOT NULL,
  `id_following` bigint(20) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `followers_id_follower_id_following_unique` (`id_follower`,`id_following`),
  KEY `followers_id_following_foreign` (`id_following`),
  CONSTRAINT `followers_id_follower_foreign` FOREIGN KEY (`id_follower`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `followers_id_following_foreign` FOREIGN KEY (`id_following`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE `notifications` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) unsigned NOT NULL,
  `id_follower` bigint(20) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `notifications_id_user_foreign` (`id_user`),
  KEY `notifications_id_follower_foreign` (`id_follower`),
  CONSTRAINT `notifications_id_follower_foreign` FOREIGN KEY (`id_follower`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `notifications_id_user_foreign` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
