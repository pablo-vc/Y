# Y - Backend API

Here you will find all the information related to this project API.

At the end you will also find a quick installation guide and the database creation statements in case you want to use the app but don't want to install the web client.

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


## Getting Started

### Requirements
- Java 17+
- Apache Tomcat
- MySQL / MariaDB

### Steps
1. Clone the repository
2. Create the database using the laravel migrations or the provided SQL
3. Configure database connection in `ConnectionManager.java`
4. Deploy the project on Tomcat
5. Access API at: http://localhost:8080/apirest (url could change depending on the client)

### Testing 
http://localhost:8080/yapi/rest/users/ping

If it returns "pong" it is working properly.

## Creation Statements

### Database creation statement

CREATE DATABASE `y_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */

### Tables creation statements

CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `bio` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_username_unique` (`username`),
  UNIQUE KEY `users_email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE `posts` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) unsigned NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `posts_id_user_foreign` (`id_user`),
  CONSTRAINT `posts_id_user_foreign` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE `followers` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_follower` bigint(20) unsigned NOT NULL,
  `id_following` bigint(20) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `followers_id_follower_id_following_unique` (`id_follower`,`id_following`),
  KEY `followers_id_following_foreign` (`id_following`),
  CONSTRAINT `followers_id_follower_foreign` FOREIGN KEY (`id_follower`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `followers_id_following_foreign` FOREIGN KEY (`id_following`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE `notifications` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) unsigned NOT NULL,
  `id_follower` bigint(20) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `notifications_id_user_foreign` (`id_user`),
  KEY `notifications_id_follower_foreign` (`id_follower`),
  CONSTRAINT `notifications_id_follower_foreign` FOREIGN KEY (`id_follower`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `notifications_id_user_foreign` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
