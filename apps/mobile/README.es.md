#  Y – Móvil

Este proyecto contiene el cliente móvil de **Y**, una aplicación multiplataforma de microblogging.

Este readme contiene toda la información relacionada con la parte móvil de la aplicación, incluyendo la cronología del desarrollo y las areas de investigación relevantes para el desarrollo copmleto del proyecto.

El cliente móvil permite a los usuarios interactuar con la aplicación a través de su teléfono android.

## Idiomas

- 🇬🇧 English → [README.md](README.md)
- 🇪🇸 Español

## Características

- Feed global con publicaciones de todos los usuarios
- Feed personalizado con publicaciones de usuarios seguidos
- Posibilidad de crear y eliminar publicaciones
- Gestión del perfil de usuario
- Sistema de notificaciones de seguimiento
- Navegación fluida usando BottomNavigationView y fragments
- Feed deslizable usando ViewPager y TabLayout

## Tecnologías utilizadas

- Java
- Android SDK
- AndroidX Navigation
- RecyclerView / ViewPager
- CoordinatorLayout / CollapsingToolbarLayout
- REST API integration

## Investigación

### Videos y Cursos:

 - [Curso 1](https://www.youtube.com/watch?v=UaR7GSNACsM) - relevante
 - [Curso 2](https://www.youtube.com/watch?v=2b7bXOiOA38&list=PLAg6Lv5BbjjdvIcLQdVg4ROZnfuuQcqXB) - vídeo 39 en adelante, no tan relevante

 #### Componentes
 - [Componente de interés](https://www.youtube.com/watch?v=1N6xmCHZexo)
 - [Coordinator Layout y collapsing toolbar](https://www.youtube.com/watch?v=0qIHSn7NslE)
 - [Comportamiento personalizado de Coordinator Layout](https://www.youtube.com/watch?v=P84O-lT2p1M)
 - [Navigation Drawer y Bottom Sheet Dialog](https://www.youtube.com/watch?v=ahNruIZX130)
 - [Diálogo personalizado 1](https://www.youtube.com/watch?v=qBlYUVdYQL8)
 - [Diálogo personalizado 2](https://www.youtube.com/watch?v=I66WdS8U2hY)

 #### Información a Futuro
 - [Como publicar la app en Google Play](https://www.youtube.com/watch?v=7bhIQK26Brw)


 ### Documentación
 - [Gestión de la navegación](https://developer.android.com/reference/androidx/navigation/ui/package-summary?hl=en)
 - [FragmentContainerView](https://developer.android.com/reference/androidx/fragment/app/FragmentContainerView?hl=en)
 - [Fragments](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment?hl=en)
 - [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=es-419#java)
 - [ViewPager](https://developer.android.com/guide/navigation/navigation-swipe-view-2?hl=es-419)


<hr>

## Arquitectura del proyecto

El proyecto sigue una arquitectura modular con una separación clara de directorios por responsabilidades.

### 📁 ui
Contiene todos los componentes relacionados con la interfaz de usuario, organizada en subcarpetas independientes para cada apartado de la aplicación.

### 📁 data
Incluye la clase `Api`, encargada de la recogida y volcado de datos y comunicación con la API.

### 📁 adapters
Contiene los adaptadores utilizados para conectar los datos con componentes visuales como `RecyclerView` o `ViewPager`.  


### Justificación
Esta organización sigue el principio de separación de responsabilidades, facilitando la escalabilidad del proyecto y mejorando la localización, legibilidad y mantenimiento del código.

<hr>

## Cronoligía del proyecto

### 02/02 - 08/02

- Actividad de inicio de sesión creada.
- BottomNavigationView y fragments implementados para la navegación dentro de la app.
- Layouts XML independientes por fragment.
- Modelos de datos creados.
- Investigación sobre la gestión de notificaciones fuera de la app.
- RecyclerView y adapter para mostrar las notificaciones.
- Investigación sobre ViewPager y TabLayout para navegar entre las pestañas del Feed.
- Metodos de inicio de sesión y registro creados en la clase Api.

### 09/02 - 15/02

- Metodos de la clase Api creados y corregidos.
- Avances en la lógica principal de la app (adaptadores, actividades principales).
- Validación de campos en login y registro.
- CreatePostActivity terminada (sin comunicación con la API todavía)
- FeedFragment funcional con ViewPager y TabLayout
- PostAdapter ahora identifica el propietario de las publicaciones.
- Diseño del perfil mejorado con CoordinatorLayout, CollapsingToolbarLayout and AppBarLayout.
- Diálogo personalizado para editar la información de usuario.
- Paleta de colores y estilos globales implementados.

### 16/02 - 22/02

- Últimos retoques en la interfaz gráfica de la aplicación 
  - Activity con el perfil de otro usuario al pulsar en su publicación, con opción de seguirlo.
  - Opción de eliminar cuenta en el apartado de editar perfil.

- Login, registro, feed, perfil, notificaciones, edición y borrado de usuarios completamente funcional con integración de la API.


## Resultado final

El cliente móvil se completó con todas las funcionalidades principales integradas con la API del backend.


## Video Demo

[Ver](https://drive.google.com/file/d/10z0-qfVdepgBj-o2TFCdCBlAs4GhfpPl/view?usp=sharing)

