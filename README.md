# Y - Microblogging Multiplatform Application

This repository contains the development process, technical documentation and resources for **Y**, a cross-platform microblogging application inspired by Twitter.

The app focuses on text-based posts and minimalistic interaction, providing a space where users can share their thoughts and interests.

## Languages

- 🇬🇧 English 
- 🇪🇸 Español → [README.es.md](README.es.md)

## Features

- Global feed with posts from all users
- Personalized feed with posts from followed users
- Possibility to follow other users
- User profiles with editable information
- Post management (create and delete posts)
- Notification system for new followers

## Screenshots

### Web Application

<p align="center">
  <img src="https://github.com/pablo-vc/Y/blob/main/docs/resources/web/notifications.JPG" width="30%">
  <img src="https://github.com/pablo-vc/Y/blob/main/docs/resources/web/feed.JPG" width="30%">
  <img src="https://github.com/pablo-vc/Y/blob/main/docs/resources/web/profile.JPG" width="30%">
</p>

### Mobile Application

<p align="center">
  <img src="docs/resources/mobile/feed.jpg" width="30%">
  <img src="docs/resources/mobile/profile.jpg" width="30%">
  <img src="docs/resources/mobile/notifications.jpg" width="30%">
</p>

### Desktop Application

<p align="center">
  <img src="docs/resources/desktop/feed.jpg" width="30%">
  <img src="docs/resources/desktop/profile.jpg" width="30%">
  <img src="docs/resources/desktop/notifications.jpg" width="30%">
</p>

## Architecture

The project consists of a backend REST API and three client applications:

- Web application
- Mobile application
- Desktop application

All clients communicate with the same backend service.

## Project Structure

```
.
├ apps
│  ├ web             
│  │   ├ readme
│  │   └ app         Web Client 
│  │
│  ├ mobile          
│  │   ├ readme
│  │   └ app        Mobile Client
│  │ 
│  └ desktop        
│      ├ readme
│      └ app        Desktop Client
│
├ backend           
│   ├ readme 
│   └ api           REST API server
│
└ docs              Documentation and resources
   ├resources       
   │
   ├ en 
   │  ├ proyect memory
   │  └ technical documetation
   │
   └ es
      ├ memoria de proyecto
      └ documentación técnica


```

## Platforms and Backend

Each subproject has its own documentation and development log:

- [Web](apps/web/README.md)
- [Mbile](apps/mobile/README.md)
- [Desktop](apps/desktop/README.md)
- [API](backend/README.md)

## System Architecture
```
Clients
 ├ Web
 ├ Mobile
 └ Desktop
        │
        │ 
        ▼
     API REST
        │
        ▼
    Database
```
All clients communicate with the same backend service.
