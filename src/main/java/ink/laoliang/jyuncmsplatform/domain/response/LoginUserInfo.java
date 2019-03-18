package ink.laoliang.jyuncmsplatform.domain.response;

import ink.laoliang.jyuncmsplatform.domain.User;

public class LoginUserInfo {

    private String token;

    private User user;

    public LoginUserInfo(String token, User user) {
        this.token = token;
        this.user = user;
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
