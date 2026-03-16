package apirest.models;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notification {
    private int id;
    private int id_user;
    private int id_follower;
    private String username;
    private String created_at;
    

    public Notification(){}

    public Notification(int id, int idUser, int id_follower, String username, String created_at) {
        this.id = id;
        this.id_user = idUser;
        this.id_follower = id_follower;
        this.username = username;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_follower() {
        return id_follower;
    }

    public void setId_follower(int idFollower) {
        this.id_follower = idFollower;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int idUser) {
        this.id_user = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String content) {
        this.username = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String createdAt) {
        this.created_at = createdAt;
    }

}
