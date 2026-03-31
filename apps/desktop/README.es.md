#  Y – Escritorio

Este proyecto contiene el cliente de escritorio de **Y**, una aplicación multiplataforma de microblogging.

Este README contiene toda la información relacionada con la versión de escritorio de la aplicación, incluyendo la cronología del desarrollo y las areas de investigación relevantes para la evolución completa del proyecto.

El cliente de escritorio permite a los usuarios interactuar conla aplicación desde su ordenador de forma intuitiva y eficiente.

## Idiomas

- 🇬🇧 English → [README.md](README.es.md)
- 🇪🇸 Español 

## Características

- Feed global con publicaciones de todos los usuarios
- Feed con publicaciones de usuarios seguidos
- Posibilidad de crear y eliminar publicaciones
- Gestión del perfil e información de usuario
- Sistema de notificaciones de seguimiento

## Tecnologías utilizadas

- C#
- .NET
- Windows Forms

## Estructura

```
.
├ Controllers
│
├ Forms           
│   └ Controls           
│
├ Models
│
├ Resources
│
├ Services             
│  ├ ApiService.cs
│  └ Session.cs                 
│
└ Program.cs

```


El proyecto sigue una arquitectura modular con una separación clara de responsabilidades por directorios.

### 📁 Controllers
Contiene las clases encargadas de la comunicación con la API mediante peticiones http.

### 📁 Forms
Contiene todos los formularios de la aplicación.
#### 📁 Controls
Contiene los controles personalizados utilizados en los distintos formularios.

### 📁 Models
Contiene los modelos de datos utilizados tanto para la comunicación con la API como para la estructuración interna de la aplicación.

### 📁 Resources
Almacena las imágenes e iconos utilizados en el proyecto.

### 📁 Services
Incluye:
    - **ApiService**: centraliza las llamadas HTTP a los controladores.
    - **Session**: gestiona la información del usuario en la sesión actual.  


## Cronología

### 09/03 - 15/03
- Modelos de datos y DTOs definidos
- Controladores creados (métodos http)
- Clase `ApiService` creada (centralizacion de llamadas http)
- `LoginForm` creado

### 16/03 - 22/03
- `LoginForm` integrado con la API
- `PostCardControl` creado (control personalizado publicaciones)
- Implementación del sistema de navegación
    - `MainForm` creado
    - `FeedControl`, `ProfileControl` y `NotificationControl` creados
    - `MainForm` renderiza el control correspondiente en función de un enumerado
- Controladores y `ApiService` terminados

### 23/03 - 27/03
- `RegisterForm` creado
- Nuevas funcionalidades:
    - Editar perfil
    - Acceder al perfil de otros usuarios desde el feed
    - Seguir/dejar de seguir usuarios
    - Cierre de sesión (logout)
    - Eliminar cuenta
- `NotificationCardControl` implementado (control personalizado para notificaciones)
- Acceso al perfil de otros usuarios desde notificaciones
- Feed dividido en:
    - Global
    - Siguiendo


## Video Demo

[Ver](https://drive.google.com/file/d/1U9I_zs0BfAeEFG3J-1px0wvpDAsgRuMg/view?usp=sharing)
