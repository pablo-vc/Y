# Y – Mobile Client

This folder contains the mobile client of **Y**, a cross-platform microblogging app.

In this readme you will find all the information related to the mobile client, including a timeline and the relevant research areas that had impact on the development of the project.

The mobile app allows users to interact with the app via Android devices.

## Idiomas

- 🇬🇧 English
- 🇪🇸 Español → [README.md](README.es.md)

## Features

- Global feed with posts from all users
- Personalized feed from followed users
- Create and delete posts
- User profile management with edit and delete options
- Notifications for new followers
- Smooth navigation using BottomNavigationView and fragments
- Swipeable feed using ViewPager and TabLayout

## Technologies

- Java
- Android SDK
- AndroidX Navigation
- RecyclerView / ViewPager
- CoordinatorLayout / CollapsingToolbarLayout
- REST API integration

## Research

During development, the following resources were consulted:

### Videos and Courses:

 - [Curso 1](https://www.youtube.com/watch?v=UaR7GSNACsM) - relevante
 - [Curso 2](https://www.youtube.com/watch?v=2b7bXOiOA38&list=PLAg6Lv5BbjjdvIcLQdVg4ROZnfuuQcqXB) - vídeo 39 en adelante, no tan relevante

 #### Components
 - [Interesting component](https://www.youtube.com/watch?v=1N6xmCHZexo)
 - [Coordinator Layout and collapsing toolbar](https://www.youtube.com/watch?v=0qIHSn7NslE)
 - [Coordinator Layout Custom Behaviours](https://www.youtube.com/watch?v=P84O-lT2p1M)
 - [Navigation Drawer and Bottom Sheet Dialog](https://www.youtube.com/watch?v=ahNruIZX130)
 - [Custom Dialog 1](https://www.youtube.com/watch?v=qBlYUVdYQL8)
 - [Custom Dialog 2](https://www.youtube.com/watch?v=I66WdS8U2hY)

 #### Information for the Future 
 - [How to publish the app in Google Play](https://www.youtube.com/watch?v=7bhIQK26Brw)


 ### Documentation
 - [Navigation management](https://developer.android.com/reference/androidx/navigation/ui/package-summary?hl=en)
 - [FragmentContainerView](https://developer.android.com/reference/androidx/fragment/app/FragmentContainerView?hl=en)
 - [Fragments](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment?hl=en)
 - [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=es-419#java)
 - [ViewPager](https://developer.android.com/guide/navigation/navigation-swipe-view-2?hl=es-419)


## Architecture

The project follows a modular architecture with clear separation of responsibilities:

### 📁 ui
Contains all UI components, organized into subfolders per screen/section of the app.

### 📁 data
Contains the `Api` class, in charge of data handling and communication with the backend.

### 📁 adapters
Contains adapters for connecting data to UI components like `RecyclerView` and `ViewPager`.

This organization allows scalability, easier maintenance and better readability.

<hr>

## Development Log

### 02/02 – 08/02

- Login activity created.
- BottomNavigationView and fragments implemented for app navigation.
- Independent XML layouts per fragment.
- Data models created.
- Notification management researched. (outside the app)
- Notifications shown with a RecyclerView and custom Adapter.
- ViewPager and TabLayout researched for feed navigation.
- Initial API communication setup.

### 09/02 – 15/02

- API class methods defined and refined.
- Core app logic completed (adapters, main activities).
- Validation for login and registration forms.
- CreatePostActivity designed (without backend integration yet).
- FeedFragment fully functional with ViewPager and TabLayout.
- PostAdapter now identifies the post owner.
- Profile page design improved using CoordinatorLayout, CollapsingToolbarLayout, AppBarLayout.
- Custom dialog implemented for profile editing.
- Complete custom color pallete implemented.
- Global styles implemented for a better look.

### 16/02 – 22/02
- Final UI tweaks.
- Profile activity for other users completed (with follow option).
- Account deletion implemented in profile edit.
- Login, registration, feed, profile, notifications, user editing and deletion fully functional with API integration.

### Final Result

The mobile client was completed with all core functionalities integrated with the backend API.

## Demo Video

[Watch](https://drive.google.com/file/d/10z0-qfVdepgBj-o2TFCdCBlAs4GhfpPl/view?usp=sharing)
