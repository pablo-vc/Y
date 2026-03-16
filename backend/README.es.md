# Y - Backend API

Este proyecto contiene la API encargada de la persistencia de datos en la app.

Este readme contiene toda la información en relación a la misma.

## Idiomas

- 🇬🇧 English → [README.md](README.md)
- 🇪🇸 Español 

## Responsibilities

- Autenticación (log-in/registro)
- Gestión de posts (crear/borrar)
- Generación del Feed (global/siguiendo)
- Notificaciones 
- Gestión de seguidores

## Tecnologias

- Java
- Apache Tomcat Server
- MySQL (database)

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
| POST   | /users/login    | Inicio de sesión |
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
| GET    | /followers/isFollowing/{id_follower}/{id_following} | Verificar si sigue |
| GET    | /followers/{id}/followersCount | Número de seguidores |
| GET    | /followers/{id}/followingCount | Número de usuarios seguidos |


## Clases auxiliares

Para facilitar la gestión de los datos enviados a la API y no enviar y recibir datos innecesarios, se crearon las siguientes clases:

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

