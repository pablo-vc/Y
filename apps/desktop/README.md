# Y – Desktop Client

This project contains the desktop client of Y, a cross-platform microblogging application.

This README gathers all the information related to the desktop version, including the development timeline and the relevant research areas for the overall evolution of the project.

The desktop client allows users to interact with the application from their computer in an intuitive and efficient way.

## Languages

- 🇬🇧 English 
- 🇪🇸 Español → [README.es.md](README.es.md)

## Features

- Global feed with posts from all users
- Custom feed from followed users
- Create and delete posts
- User profile management
- Follow notifications

## Technologies

- C#
- .NET
- Windows Forms

## Proyect Structure

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

The project follows a modular architecture, with a clear separation of responsibilities across directories.

### 📁 Controllers
Contains the classes responsible for communication with the API through HTTP requests.

### 📁 Forms
Contains all the forms used in the application.
#### 📁 Controls
Includes custom controls used across different forms.

### 📁 Models
Contains the data models used both for API communication and internal data structuring.

### 📁 Resources
Stores images and icons used in the project.

### 📁 Services
Includes:
- **ApiService**: centralizes HTTP calls to controllers  
- **Session**: manages the current user's session data  

---

## Development Timeline

### 09/03 - 15/03
- Data models and DTOs defined  
- Controllers created (HTTP methods)  
- `ApiService` class implemented (centralized HTTP calls)  
- `LoginForm` created  

### 16/03 - 22/03
- `LoginForm` integrated with the API  
- `PostCardControl` created (custom control to display posts)  
- Navigation system implemented:
  - `MainForm` created  
  - `FeedControl`, `ProfileControl`, and `NotificationControl` created  
  - `MainForm` renders the appropriate control based on an enum  
- Controllers and `ApiService` completed  

### 23/03 - 27/03
- `RegisterForm` created  
- New features added:
  - Profile editing  
  - Access to other users' profiles from the feed  
  - Follow / unfollow users  
  - Logout  
  - Account deletion  
- `NotificationCardControl` implemented (custom notification control)  
- Access to user profiles from notifications  
- Feed split into:
  - Global  
  - Following  


## Demo Video

[Watch](https://drive.google.com/file/d/1U9I_zs0BfAeEFG3J-1px0wvpDAsgRuMg/view?usp=sharing)
