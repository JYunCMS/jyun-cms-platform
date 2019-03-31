package ink.laoliang.jyuncmsplatform.domain.response;

import ink.laoliang.jyuncmsplatform.domain.User;

public class LoginUserInfo {

    private Boolean status;

    private String message;

    private String token;

    private User user;

    public LoginUserInfo(Boolean status, String message, String token, User user) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.user = user;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
