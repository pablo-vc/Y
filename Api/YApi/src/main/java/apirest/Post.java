package apirest;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Post {
    private int id;
    private int id_user;
    private String content;
    private String created_at;
    private String username;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Post(){}

    public Post(int id, int id_user, String content, String created_at, String username) {
        this.id = id;
        this.id_user = id_user;
        this.content = content;
        this.created_at = created_at;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int userId) {
        this.id_user = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String createdAt) {
        this.created_at = createdAt;
    }

}
