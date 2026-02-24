package com.example.y;

public class Notification {
    private int id;
    private int fromUserId;
    private int  toUserId;
    private String fromUsername;
    private String createdAt;

    public Notification(int id, int fromUserId, int toUserId, String fromUsername, String createdAt) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;

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

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }
}
