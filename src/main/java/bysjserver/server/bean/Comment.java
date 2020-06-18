package bysjserver.server.bean;

import java.sql.Date;

public class Comment {
    Integer id,communicationId;
    String userId,content;
    Date date;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", communicationId=" + communicationId +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(Integer communicationId) {
        this.communicationId = communicationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
