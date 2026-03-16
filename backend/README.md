# Y - Backend API

This project contains the API responsible for the data persistence of the app.

This README contains all the information about it.

## Languages

- 🇬🇧 English 
- 🇪🇸 Español → [README.es.md](README.es.md)

## Responsibilities

- Authentication (log-in/register)
- Post management (create/delete)
- Feed generation (global feed / following feed)
- Notifications 
- Following management

## Technologies

- Java
- Jakarta REST (JAX-RS)
- Apache Tomcat
- MariaDB / MySQL
- JDBC

## Architecture
```
apirest/                ← Project root
 ├ config/              ← Global configuration and utilities
 │    ├ RestConfig      ← Basic url configuration
 │    └ ConnectionManager.java     ← Handles database connection management
 │
 ├ resources/                   ← REST classes (HTTP endpoints)
 │    ├ Users.java
 │    ├ Posts.java
 │    ├ Followers.java
 │    └ Notifications.java
 │
 ├ models/                      ← Database entities
 │    ├ User.java
 │    ├ Post.java
 │    ├ Follower.java
 │    └ Notification.java
 │
 └ dto/                         ← Data transfer objects(DTO)
      ├ LoginRequest.java
      ├ RegisterRequest.java
      └ UserUpdate.java
```
## Main Endpoints

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | /users/register | Register a new user |
| POST   | /users/login    | Authenticate a user |
| GET    | /users/{id}     | Retrieve user information |
| PUT    | /users/update/{id} | Update user information |
| DELETE | /users/delete/{id} | Delete a user |

---

### Posts
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | /posts               | Get all posts (global feed) |
| GET    | /posts/{id_user}     | Get posts from a specific user |
| GET    | /posts/following/{id_user} | Get posts from followed users |
| POST   | /posts/create        | Create a new post |
| DELETE | /posts/delete/{id}   | Delete a post |

---

### Notifications
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /notifications/{id_user} | Get user notifications |

---

### Followers
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | /followers/{id_follower}/follow/{id_following}   | Follow a user |
| DELETE | /followers/{id_follower}/unfollow/{id_following} | Unfollow a user |
| GET    | /followers/isFollowing/{id_follower}/{id_following} | Check if a user follows another user |
| GET    | /followers/{id}/followersCount | Count of followers |
| GET    | /followers/{id}/followingCount | Count of users followed |


## Auxiliary Classes

To simplify data handling and avoid sending unnecessary information to the API, the following DTO classes are used:

### LoginRequest
Represents the data required for user login.
- **Fields:** 
  - `email` (String): User's email
  - `password` (String): User's password

### RegisterRequest
Represents the data required for registering a new user.
- **Fields:** 
  - `username` (String): Desired username
  - `email` (String): User's email
  - `password` (String): User's password

### UserUpdate
Represents the data required to update user information.
- **Fields:** 
  - `username` (String): New username (optional)
  - `description` (String): New description (optional)
