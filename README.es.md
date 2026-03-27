# Y - Aplicación Multiplataforma de Microblogging

Este repositorio contiene la línea de desarrollo, documentación técnica y recursos de **Y**, una aplicación multiplataforma de microblogging inspirada en Twitter.

La aplicación se centra en publicaciones de texto e interacción minimalistas, brindando un espacio donde los usuarios puedan compartir sus inquietudes e intereses.

## Idiomas

- 🇬🇧 English → [README.md](README.md)
- 🇪🇸 Español 

## Características

- Feed global con publicaciones de todos los usuarios
- Feed personalizado con publicaciones de los usuarios que sigues
- Posibilidad de seguir a otros usuarios
- Perfil de usuario con información editable
- Gestión de publicaciones (publicar y borrar)
- Sistema de notificaciones para nuevos seguidores

## Imágenes

### Aplicación Web

<p align="center">
  <img src="https://github.com/pablo-vc/Y/blob/main/docs/resources/web/notifications.JPG" width="30%">
  <img src="https://github.com/pablo-vc/Y/blob/main/docs/resources/web/feed.JPG" width="30%">
  <img src="https://github.com/pablo-vc/Y/blob/main/docs/resources/web/profile.JPG" width="30%">
</p>

### Aplicación Móvil

<p align="center">
  <img src="docs/screenshots/mobile/feed.png" width="30%">
  <img src="docs/screenshots/mobile/profile.png" width="30%">
  <img src="docs/screenshots/mobile/notifications.png" width="30%">
</p>

### Aplicación de Escritorio

<p align="center">
  <img src="https://github.com/pablo-vc/Y/blob/main/docs/resources/desktop/Feed.JPG" width="30%">
  <img src="https://github.com/pablo-vc/Y/blob/main/docs/resources/desktop/Profile.JPG" width="30%">
  <img src="https://github.com/pablo-vc/Y/blob/main/docs/resources/desktop/Notifications.JPG" width="30%">
</p>

## Arquitectura

El proyecto consiste en un backend con una API Rest y tres aplicaciones de cliente:

- Aplicación web 
- Aplicación móvil 
- Aplicación de escritorio

```
             Clients
 ┌─────────┬─────────┬──────────┐
 │   Web   │  Móvil  │Escritorio│
 └─────────┴─────────┴──────────┘
                │
                ▼
             REST API
                │
                ▼
             Database
```

Todos los clientes se comunican con el mismo servicio de backend.

## Estructura del proyecto

```
.
├ apps
│  ├ web             
│  │   ├ readme
│  │   └ app         Cliente Web 
│  │
│  ├ mobile          
│  │   ├ readme
│  │   └ app        Cliente Móvil
│  │ 
│  └ desktop        
│      ├ readme
│      └ app        Cliente Escritorio
│
├ backend           
│   ├ readme 
│   └ api           Servidor API REST  
│
├ install           Versiones instalables de la aplicación
│
└ docs              Documentación y recursos
   ├resources       
   │
   ├ en 
   │  ├ project memory
   │  └ technical documentation
   │
   └ es
      ├ memoria de proyecto
      └ documentación técnica


```

## Plataformas y Backend

Cada subproyecto tiene su propia documentation junto con su registro de desarrollo:

- [Web](apps/web/README.es.md)
- [Móvil](apps/mobile/README.es.md)
- [Escritorio](apps/desktop/README.es.md)
- [API](backend/README.es.md)
