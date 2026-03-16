package apirest.models;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    private int id;
    private String username;
    private String email;
    private String bio;
    private String created_at;
    

    public User(){}
    
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

    public void setCreated_at(String createdAt) {
        this.created_at = createdAt;
    }

}
