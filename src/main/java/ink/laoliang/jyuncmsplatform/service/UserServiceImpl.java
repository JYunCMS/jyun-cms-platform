package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.User;
import ink.laoliang.jyuncmsplatform.domain.request.UpdateUserInfo;
import ink.laoliang.jyuncmsplatform.domain.response.LoginUserInfo;
import ink.laoliang.jyuncmsplatform.exception.IllegalParameterException;
import ink.laoliang.jyuncmsplatform.repository.UserRepository;
import ink.laoliang.jyuncmsplatform.util.JwtToken;
import ink.laoliang.jyuncmsplatform.util.MD5Encode;
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
    public User addNewUser(User user) {
        checkUsernameFormat(user.getUsername());
        checkPasswordFormat(user.getPassword());

        if (userRepository.findById(user.getUsername()).orElse(null) != null) {
            throw new IllegalParameterException("【非法参数异常】- 用户名 " + user.getUsername() + " 已存在！");
        }

        user.setPassword(MD5Encode.encode(user.getPassword()));

        // 返回不包含密码的用户对象
        return new User(userRepository.save(user));
    }

    @Override
    public User updateUser(UpdateUserInfo updateUserInfo) {
        User oldUser = userRepository.findById(updateUserInfo.getUser().getUsername()).orElse(null);
        if (oldUser == null) {
            throw new IllegalParameterException("【非法参数异常】- 用户名 " + updateUserInfo.getUser().getUsername() + " 不存在！");
        }

        if (!oldUser.getPassword().equals(MD5Encode.encode(updateUserInfo.getOldPassword()))) {
            throw new IllegalParameterException("【非法参数异常】- 原密码不正确！");
        }

        oldUser.setNickname(updateUserInfo.getUser().getNickname());
        oldUser.setRole(updateUserInfo.getUser().getRole());
        if (updateUserInfo.getUser().getPassword() != null && !updateUserInfo.getUser().getPassword().equals("")) {
            checkPasswordFormat(updateUserInfo.getUser().getPassword());
            oldUser.setPassword(MD5Encode.encode(updateUserInfo.getUser().getPassword()));
        }

        // 返回不包含密码的用户对象
        return new User(userRepository.save(oldUser));
    }

    @Override
    public void deleteUser(String username) {
        if (userRepository.findById(username).orElse(null) == null) {
            throw new IllegalParameterException("【非法参数异常】- 用户名 " + username + " 不存在！");
        }

        userRepository.deleteById(username);
    }

    @Override
    public LoginUserInfo login(User user) {
        User perfectUser = userRepository.findById(user.getUsername()).orElse(null);
        if (perfectUser == null) {
            throw new IllegalParameterException("【非法参数异常】- 用户名不存在！");
        }

        if (!perfectUser.getPassword().equals(MD5Encode.encode(user.getPassword()))) {
            throw new IllegalParameterException("【非法参数异常】- 密码错误！");
        }

        // 返回 Token 和 不包含密码的用户对象
        return new LoginUserInfo(JwtToken.createToken(perfectUser, JWT_SECRET_KEY), new User(perfectUser));
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
