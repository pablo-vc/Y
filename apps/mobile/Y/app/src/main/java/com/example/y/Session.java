package com.example.y;

public class Session {
    private static Session instance;
    private User currentUser;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) instance = new Session();
        return instance;
    }

    public void setUser(User user) { currentUser = user; }
    public User getUser() { return currentUser; }

    public int getUserId() {
        if (currentUser != null) {
            return currentUser.getId();  // Asumiendo que User tiene un método getId()
        }
        return -1;  // -1 indica que no hay un usuario logueado
    }

    public void logout() {
        currentUser = null;
    }

}
