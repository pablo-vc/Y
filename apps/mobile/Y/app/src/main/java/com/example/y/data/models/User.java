package com.example.y.data.models;

public class User {
    private int id;
    private String username;
    private String email;
    private String bio;
    private String created_at;

    public User(int id, String username, String email, String bio, String created_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.created_at = created_at;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCreated_at() {
        return created_at;
    }

    public static boolean validateUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]{2,20}$");
    }

    public static boolean validateEmail(String email) {
        return email.matches("^[A-Za-z0-9](?:[A-Za-z0-9._%+-]{0,62}[A-Za-z0-9])?@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");
    }

}
