#  Y – Móvil

Apartado en el que se irán exponiendo los avances semanales en el desarrollo de la aplicación móvil.

## Investigación

 - [Curso 1](https://www.youtube.com/watch?v=UaR7GSNACsM)
 - [Componente de interés](https://www.youtube.com/watch?v=1N6xmCHZexo)
 - [Curso 2](https://www.youtube.com/watch?v=2b7bXOiOA38&list=PLAg6Lv5BbjjdvIcLQdVg4ROZnfuuQcqXB) - vídeo 39 en adelante
 - [Información a futuro, como publicar la app en Google Play](https://www.youtube.com/watch?v=7bhIQK26Brw)
 - [Gestión de la navegación](https://developer.android.com/reference/androidx/navigation/ui/package-summary?hl=en)
 - [FragmentContainerView](https://developer.android.com/reference/androidx/fragment/app/FragmentContainerView?hl=en)
 - [Fragments](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment?hl=en)
 - [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=es-419#java)
 - [ViewPager](https://developer.android.com/guide/navigation/navigation-swipe-view-2?hl=es-419)
 - [Coordinator Layout y collapsing toolbar](https://www.youtube.com/watch?v=0qIHSn7NslE)
 - [Coordinator Layout Custom Behaviours](https://www.youtube.com/watch?v=P84O-lT2p1M)
 - [Navigation Drawer y Bottom Sheet Dialog](https://www.youtube.com/watch?v=ahNruIZX130)
 - [Diálogo personalizado 1](https://www.youtube.com/watch?v=qBlYUVdYQL8)
 - [Diálogo personalizado 2](https://www.youtube.com/watch?v=I66WdS8U2hY)

<hr>

## Arquitectura del proyecto

El proyecto se organiza a partir de una carpeta raíz que separa las distintas responsabilidades de la aplicación en tres paquetes principales: ui, data y adapters, fuera de los cuales se encuentran los modelos de datos, para fácil acceso desde cualquier parte de la aplicación. Esta división permite mantener una arquitectura clara, modular y fácil de mantener.

### 📁 ui
Contiene todos los componentes relacionados con la interfaz de usuario.  
Esta carpeta se ha organizado en subcarpetas independientes para cada apartado de la aplicación.

### 📁 data
Incluye la clase Api, la cual contiene las funciones que se encargarán de la recogida y volcado de datos a la base de datos.

### 📁 adapters
Contiene los adaptadores utilizados para conectar los datos con componentes visuales como `RecyclerView` o `ViewPager`.  
Al tratarse de clases reutilizables por distintas pantallas, se mantienen fuera de la capa de interfaz de usuario.

### Justificación
Esta organización sigue el principio de separación de responsabilidades, facilitando la escalabilidad del proyecto y mejorando la localización, legibilidad y mantenimiento del código.

<hr>

## Cronoligía del proyecto

### 02/02 - 08/02

Creación de la actividad de inicio de sesion.

Implementación de bottomNavigationView y fragments para la gestión de la navegación una vez dentro de la aplicación. Cada fragment utiliza un layout XML independiente que define su estructura visual, mientras que los elementos individuales de listas se representan mediante layouts reutilizables.

Creación de los modelos de datos de la aplicación.

Investigación sobre la gestión de notificaciones, se mostrarán mediante un RecyclerView, utilizando un Adapter personalizado que adapta el contenido de cada notificación en función de su tipo (seguimiento o nuevo post).

Investigación sobre viewPager y TabLayout para la gestión del feed para mostrar diferentes vistas del contenido.

Primeros pasos en la gestión de la comunicación con la Api.

### 09/02 - 15/02

Definición y retocado de algunas funciones de la clase Api.

Grandes avances en la mayoría de la lógica interna de la aplicación (adapters y activities principales, muestran datos externos a la base de datos)

Validación de datos en las activities de registro e inicio de sesión;

Diseño y creación de "CreatePostActivity" terminado, sin comunicación con la base de datos todavía.

Diseño general de la aplicación mejorado, implementación de una paleta de colores completa y estilos para inputs y botones.

ViewPager y TabLayout añadidos y funcionando en "FeedFragment".

PostAdapter adaptado para identificar publicaciones propias del usuario de la sesión y permitir eliminarlas en la pagina de perfil, la cual aún está en desarrollo.

Gran avance en el diseño de la página de perfil, uso de CoordinatorLayout, CollapsingToolbarLayout y AppBarLayout para ocultar la parte de arriba del perfil al deslizar hacia abajo y que se muestren solo las publicaciones y el tabLayout.

Implementación de un cuadro de diálogo personalizado para editar la información de perfil. 

### 16/02 - 22/02

### Últimos retoques en la interfaz gráfica de la aplicación 

Activity con el perfil de un usuario al pulsar en su publicación, con opción de seguirlo.

Opción de eliminar cuenta en el apartado de editar perfil.

### Comunicación con la Api

Login y registro funcionando con comunicacion a la Api.

Feed enlazado con la Api.

Perfil de otros usuarios enlazado con la Api.

Notificaciones enlazadas con la Api.

Edición de usuario funcional.

Función de elimincar usuario funcional.

Aplicación terminada.

## Video Demo




https://github.com/user-attachments/assets/0213fc2f-cde1-42af-82cd-a3a2b6ca468d



