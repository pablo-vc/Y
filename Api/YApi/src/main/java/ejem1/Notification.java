package ejem1;

public class Notification {
    private int id;
    private String type;
    private String fromUsername;
    private String content;
    private String createdAt;
    private boolean read;

    public Notification(int id, String type, String fromUsername,
            String postContent, String createdAt, boolean read) {
        this.id = id;
        this.type = type;
        this.fromUsername = fromUsername;
        this.content = postContent;
        this.createdAt = createdAt;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
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
}
