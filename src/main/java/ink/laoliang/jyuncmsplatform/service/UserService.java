package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.User;
import ink.laoliang.jyuncmsplatform.domain.request.UpdateUserInfo;
import ink.laoliang.jyuncmsplatform.domain.response.LoginUserInfo;

import java.util.List;

public interface UserService {

    List<User> getUserList();

    User addNewUser(String USER_ROLE, User user);

    User updateUser(String USER_ROLE, UpdateUserInfo updateUserInfo);

    void deleteUser(String USER_ROLE, String username);

    LoginUserInfo login(User user);
}
