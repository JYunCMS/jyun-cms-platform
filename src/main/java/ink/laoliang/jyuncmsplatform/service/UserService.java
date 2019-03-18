package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.User;
import ink.laoliang.jyuncmsplatform.domain.request.UpdateUserInfo;
import ink.laoliang.jyuncmsplatform.domain.response.LoginUserInfo;

import java.util.List;

public interface UserService {
    List<User> getUserList();

    User addNewUser(User user);

    User updateUser(UpdateUserInfo updateUserInfo);

    void deleteUser(String username);

    LoginUserInfo login(User user);
}
