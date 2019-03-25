package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.User;
import ink.laoliang.jyuncmsplatform.domain.request.UpdatePasswordInfo;
import ink.laoliang.jyuncmsplatform.domain.response.LoginUserInfo;
import ink.laoliang.jyuncmsplatform.exception.IllegalParameterException;
import ink.laoliang.jyuncmsplatform.exception.UserRolePermissionException;
import ink.laoliang.jyuncmsplatform.repository.UserRepository;
import ink.laoliang.jyuncmsplatform.util.JwtToken;
import ink.laoliang.jyuncmsplatform.util.MD5Encode;
import ink.laoliang.jyuncmsplatform.config.UserRoleFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Value("${custom.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUserList() {
        return userRepository.getAllNotIncludedPassword();
    }

    @Override
    public List<User> addNewUser(String USER_ROLE, User user) {
        // 验证用户角色权限
        if (UserRoleFields.getUserRoleLevel(USER_ROLE) < UserRoleFields.getUserRoleLevel(user.getRole())) {
            throw new UserRolePermissionException("【用户角色权限异常】- 不能创建比当前用户角色等级更高的用户！");
        }

        checkUsernameFormat(user.getUsername());
        checkPasswordFormat(user.getPassword());

        if (userRepository.findById(user.getUsername()).orElse(null) != null) {
            throw new IllegalParameterException("【非法参数异常】- 用户名 " + user.getUsername() + " 已存在！");
        }

        user.setPassword(MD5Encode.encode(user.getPassword()));

        userRepository.save(user);
        return userRepository.getAllNotIncludedPassword();
    }

    @Override
    public List<User> updateUser(String USER_ROLE, User user) {
        User oldUser = userRepository.findById(user.getUsername()).orElse(null);
        if (oldUser == null) {
            throw new IllegalParameterException("【非法参数异常】- 用户 " + user.getUsername() + " 不存在！");
        }

        // 验证用户角色权限
        if (UserRoleFields.getUserRoleLevel(USER_ROLE) <= UserRoleFields.getUserRoleLevel(oldUser.getRole())) {
            throw new UserRolePermissionException("【用户角色权限异常】- 只能更新角色等级比自己低的用户信息！");
        }
        if (UserRoleFields.getUserRoleLevel(USER_ROLE) < UserRoleFields.getUserRoleLevel(user.getRole())) {
            throw new UserRolePermissionException("【用户角色权限异常】- 不能将角色修改为比自己等级更高的角色！");
        }

        oldUser.setNickname(user.getNickname());
        oldUser.setRole(user.getRole());

        userRepository.save(oldUser);
        return userRepository.getAllNotIncludedPassword();
    }

    @Override
    public List<User> deleteUser(String USER_ROLE, String username) {
        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            throw new IllegalParameterException("【非法参数异常】- 用户 " + username + " 不存在！");
        }

        // 验证用户角色权限
        if (UserRoleFields.getUserRoleLevel(USER_ROLE) <= UserRoleFields.getUserRoleLevel(user.getRole())) {
            throw new UserRolePermissionException("【用户角色权限异常】- 只能删除比当前用户角色等级更低的用户！");
        }

        userRepository.deleteById(username);
        return userRepository.getAllNotIncludedPassword();
    }

    @Override
    public LoginUserInfo login(User user) {
        User perfectUser = userRepository.findById(user.getUsername()).orElse(null);

        if (perfectUser == null) {
            return new LoginUserInfo(false, "【登录失败】- 用户名不存在！", null, null);
        }

        if (!perfectUser.getPassword().equals(MD5Encode.encode(user.getPassword()))) {
            return new LoginUserInfo(false, "【登录失败】- 密码错误！", null, null);
        }

        // 返回 Token 和 不包含密码的用户对象
        return new LoginUserInfo(true, "登陆成功！", JwtToken.createToken(perfectUser, JWT_SECRET_KEY), new User(perfectUser));
    }

    @Override
    public User updateSelfInfo(String USER_ROLE, User user) {
        User oldUser = userRepository.findById(user.getUsername()).orElse(null);
        if (oldUser == null) {
            throw new IllegalParameterException("【非法参数异常】- 用户 " + user.getUsername() + " 不存在！");
        }

        if (UserRoleFields.getUserRoleLevel(USER_ROLE) < UserRoleFields.getUserRoleLevel(user.getRole())) {
            throw new UserRolePermissionException("【用户角色权限异常】- 不能选择比当前用户角色等级更高的角色！");
        }

        oldUser.setNickname(user.getNickname());
        oldUser.setRole(user.getRole());
        return userRepository.save(oldUser);
    }

    @Override
    public User updateSelfPassword(UpdatePasswordInfo updatePasswordInfo) {
        checkPasswordFormat(updatePasswordInfo.getUser().getPassword());

        User oldUser = userRepository.findById(updatePasswordInfo.getUser().getUsername()).orElse(null);
        if (oldUser == null) {
            throw new IllegalParameterException("【非法参数异常】- 用户 " + updatePasswordInfo.getUser().getUsername() + " 不存在！");
        }

        if (!MD5Encode.encode(updatePasswordInfo.getOldPassword()).equals(oldUser.getPassword())) {
            throw new IllegalParameterException("【非法参数异常】- 原密码不正确！");
        }

        oldUser.setPassword(MD5Encode.encode(updatePasswordInfo.getUser().getPassword()));
        return userRepository.save(oldUser);
    }

    @Override
    public User resetPassword(String USER_ROLE, User user) {
        checkPasswordFormat(user.getPassword());

        // 验证用户角色权限
        if (UserRoleFields.getUserRoleLevel(USER_ROLE) <= UserRoleFields.getUserRoleLevel(user.getRole())) {
            throw new UserRolePermissionException("【用户角色权限异常】- 只能重置角色等级比自己低的用户密码！");
        }

        User oldUser = userRepository.findById(user.getUsername()).orElse(null);
        if (oldUser == null) {
            throw new IllegalParameterException("【非法参数异常】- 用户 " + user.getUsername() + " 不存在！");
        }

        oldUser.setPassword(MD5Encode.encode(user.getPassword()));

        return userRepository.save(oldUser);
    }

    private void checkUsernameFormat(String username) {
        if (username == null || username.trim().equals("")) {
            throw new IllegalParameterException("【非法参数异常】- 用户名不能为空！");
        }
        if (!Pattern.compile("^\\w{5,20}$").matcher(username).matches()) {
            throw new IllegalParameterException("【非法参数异常】- 用户名要求 5-20 位常规 ASCII 字符！");
        }
    }

    private void checkPasswordFormat(String password) {
        if (password == null || password.trim().equals("")) {
            throw new IllegalParameterException("【非法参数异常】- 密码不能为空！");
        }
        if (!Pattern.compile("^\\w{6,20}$").matcher(password).matches()) {
            throw new IllegalParameterException("【非法参数异常】- 密码要求 6-20 位常规 ASCII 字符！");
        }
    }
}
