package com.example.y.data.models;

public class Notification {
    private int id;
    private int id_user;
    private int id_follower;
    private String fromUsername;
    private String createdAt;

    public Notification(int id, int id_user, int id_follower, String fromUsername, String createdAt) {
        this.id = id;
        this.id_user = id_user;
        this.id_follower = id_follower;
        this.fromUsername = fromUsername;
        this.createdAt = createdAt;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_follower() {
        return id_follower;
    }

    public void setId_follower(int id_follower) {
        this.id_follower = id_follower;
    }
}
