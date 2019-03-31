package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.User;
import ink.laoliang.jyuncmsplatform.domain.request.UpdatePasswordInfo;
import ink.laoliang.jyuncmsplatform.domain.response.LoginUserInfo;

import java.util.List;

public interface UserService {

    List<User> getUserList();

    List<User> addNewUser(String USER_ROLE, User user);

    List<User> updateUser(String USER_ROLE, User user);

    List<User> deleteUser(String USER_ROLE, String username);

    LoginUserInfo login(User user);

    User updateSelfInfo(String USER_ROLE, User user);

    User updateSelfPassword(UpdatePasswordInfo updatePasswordInfo);

    User resetPassword(String USER_ROLE, User user);
}
