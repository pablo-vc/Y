# Y – Cliente Web

Este proyecto contiene el cliente web de **Y**, una aplicación multiplataforma de microblogging.

Este readme contiene toda la información relacionada con la parte web de la aplicación, incluyendo la cronología del desarrollo y las areas de investigación relevantes para el desarrollo copmleto del proyecto.

El cliente web permite a los usuarios interactuar con la aplicación a través de un navegador.

## Idiomas

- 🇬🇧 English → [README.md](README.md)
- 🇪🇸 Español

## Características

- Feed global con publicaciones de todos los usuarios
- Feed personalizado con publicaciones de usuarios seguidos
- Posibilidad de crear y eliminar publicaciones
- Gestión del perfil de usuario
- Sistema de notificaciones de seguimiento

## Tecnologías utilizadas

- HTML
- CSS
- PHP
- JavaScript
- Laravel


## Áreas de Investigación

Durante el desarrollo del proyecto se investigaron y utilizaron los siguientes recursos:

- [Laravel](https://www.youtube.com/watch?v=aljDqJCYkIc&list=PLDllzmccetSM50U0Y9fTOWHvSzAZ_W6Il&index=2)
- [HTML y CSS](https://www.youtube.com/watch?v=id11DxHyQ1k&list=PLDllzmccetSPC_dJxvoyqF5xFMAHklHIl)  (Por encima)

## Cronología del proyecto

### 26/01 - 01/02

- Definición y estructuración inicial del proyecto.
- Organización de carpetas y archivos.
- Creación de una hoja de estilos común (`main.css`) y hojas de estilo individuales para cada página.
- Desarrollo de la página de inicio de sesión y registro.

### 09/02 - 15/02

- Creación del proyecto final utilizando el framework Laravel.
- Implementación del layout principal con barra de navegación.
- Creación de hojas de estilo CSS para las diferentes vistas tras iniciar sesión.
- Primeros avances en el diseño de la interfaz general de la aplicación, especialmente en el feed.

### 16/02 - 22/02

- Diseño e implementación del esquema relacional de la base de datos mediante el sistema de migraciones de Laravel.
- Aplicación de las migraciones a la base de datos utilizando el comando `php artisan migrate`.
- Desarrollo de la comunicación con la API mediante rutas, controladores y una clase (`ApiService.php`) que gestiona las llamadas HTTP.
- Implementación de la gestión de usuarios:
  - Registro
  - Inicio de sesión
  - Eliminación de cuentas
- Desarrollo de la barra de navegación con acceso a todas las secciones de la aplicación.
- Implementación de las funciones de publicar y cerrar sesión.
- Desarrollo de la página de perfil concluido con:
  - Nombre de usuario
  - Descripción
  - Contador de seguidores y seguidos
  - Edición del perfil
  - Eliminación de cuenta
  - Eliminación de publicaciones
- Implementación de la creación y eliminación de publicaciones conectadas con la API.
- Desarrollo del feed concluido con dos secciones:
  - Publicaciones globales
  - Publicaciones de usuarios seguidos.


## Resultado final

El cliente web se completó con todas las funcionalidades principales integradas con la API del backend.


## Avances después de la entrega
- Mejoras en el diseño
  - Look general de la aplicación mejorado
    - Pequeños cambios en bordes, sombras, placeholders, margenes y paddings
  - Scroll arreglado cuando hay muchas publicaciones/notificaciones, la sección principal de la página se mantiene fijada
  - App responsiva a diferentes tamaños de pantalla
  - Acceso a perfil de los seguidores desde la pestaña de notificaciones
  - Fecha de publicación añadida a las publicaciones
- Mejoras de código
  - Reorganización y unificación de estilos, estilos generales definidos en `app.blade.php` con variaciones en cada pagina
