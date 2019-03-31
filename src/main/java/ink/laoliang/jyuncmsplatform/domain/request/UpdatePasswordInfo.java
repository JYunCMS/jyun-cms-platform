package ink.laoliang.jyuncmsplatform.domain.request;

import ink.laoliang.jyuncmsplatform.domain.User;

public class UpdatePasswordInfo {

    private String oldPassword;

    private User user;

    public UpdatePasswordInfo() {
    }

    public UpdatePasswordInfo(String oldPassword, User user) {
        this.oldPassword = oldPassword;
        this.user = user;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
