# Y – Web Client

This project contains the web client of **Y**, a cross-platform microblogging platform.

This readme contains all the information related to the web client, including a timeline and the relevant research areas that had impact on the development of the project.

The web client allows users to interact with the app through a browser.

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

- HTML
- CSS
- PHP
- JavaScript
- Laravel

## Research Areas

The following resources were used during the research phase of the project:

- [Laravel](https://www.youtube.com/watch?v=aljDqJCYkIc&list=PLDllzmccetSM50U0Y9fTOWHvSzAZ_W6Il&index=2)

- [HTML y CSS](https://www.youtube.com/watch?v=id11DxHyQ1k&list=PLDllzmccetSPC_dJxvoyqF5xFMAHklHIl) (general review)

## Development Log

### 26/01 – 01/02

- Initial definition of the project structure.
- Organization of folders and files.
- Creation of a shared stylesheet (`main.css`) and individual stylesheets for each page.
- Login and registration page completed.

### 09/02 – 15/02

- Creation of the Laravel project.
- Implementation of the main layout with navigation bar.
- CSS styling for the application layout.
- Initial work on the main interface, especially the feed.

### 16/02 – 22/02

- Design and implementation of the relational database schema using Laravel migrations.
- Database successfully generated using `php artisan migrate`.
- Development of API communication, including routes and controllers.
- User management implemented (registration, login and deletion).
- Navigation bar implemented with access to all main sections.
- Functional options for publishing posts and logging out.
- Profile page implemented:
  - Username
  - Description
  - Followers and following counters
  - Edit profile form
  - Account deletion option
  - Post deletion with immediate update.
- Post creation and deletion connected to the API.
- Feed implementation:
  - Global posts
  - Posts from followed users.


### Final Result

The web client was completed with all core functionalities integrated with the backend API.


## After Project Presentation

- Design improvements
  - Better general aplication look
    - Minor changes to borders, shadows, placeholders, margins and paddings
  - Scroll bug fixed when there is a considerable number of posts/notifications, now the main interface stands still
  - Completely responsive app
  - Access to followers profiles from notification page
  - Publish date added to posts
- Code improvements
  - Style re-organization and unification, general styles defined in `app.blade.php` with derivatives in each page files


## Demo Video

[Watch](https://drive.google.com/file/d/1_GS0YerNjF6QLwMy5gS570bnW3FQjaRb/view?usp=sharing)
