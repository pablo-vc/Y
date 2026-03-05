# Y Backend API

This proyect contains the API responsible of the data perssistance of the app.

This readme contains all the information about it.

## Languages

- 🇬🇧 English 
- 🇪🇸 Español → [README.es.md](README.es.md)

## Responsibilities

- Authentication (log-in/register)
- Post management (create/delete)
- Feed generation (filtered/unfiltered)
- Notifications 
- Following management

## Technologies

- Java
- Apache Tomcat Server
- MySQL (database)


## Main Endpoints

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | /users/register | Register a new user |
| POST   | /users/login    | User login |
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
| GET    | /followers/isFollowing/{id_follower}/{id_following} | Check if following |
| GET    | /followers/{id}/followersCount | Count of followers |
| GET    | /followers/{id}/followingCount | Count of users followed |


## Auxiliary Classes

To facilitate the handling of data sent to the API and minimize the unneeded data sent and received, the following classes were created:

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