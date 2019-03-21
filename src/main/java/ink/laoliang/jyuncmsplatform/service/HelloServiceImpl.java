package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.User;
import ink.laoliang.jyuncmsplatform.domain.request.InitSystemInfo;
import ink.laoliang.jyuncmsplatform.domain.response.LoginUserInfo;
import ink.laoliang.jyuncmsplatform.repository.UserRepository;
import ink.laoliang.jyuncmsplatform.util.JwtToken;
import ink.laoliang.jyuncmsplatform.util.MD5Encode;
import ink.laoliang.jyuncmsplatform.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class HelloServiceImpl implements HelloService {

    @Value("${custom.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    private final UserRepository userRepository;

    @Autowired
    public HelloServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean alreadyInitSystem() {
        return userRepository.findById("admin").orElse(null) != null;
    }

    @Override
    public LoginUserInfo initSystem(InitSystemInfo initSystemInfo) {
        if (initSystemInfo.getAdminPassword() == null || initSystemInfo.getAdminPassword().trim().equals("")) {
            return new LoginUserInfo(false, "密码不能为空！", null, null);
        }
        if (!Pattern.compile("^\\w{6,20}$").matcher(initSystemInfo.getAdminPassword()).matches()) {
            return new LoginUserInfo(false, "密码要求 6-20 位常规 ASCII 字符！", null, null);
        }

        User adminUser = userRepository.save(new User("admin", MD5Encode.encode(initSystemInfo.getAdminPassword()), "", UserRole.SUPER_ADMIN));
        return new LoginUserInfo(true, "超级管理员已创建！", JwtToken.createToken(adminUser, JWT_SECRET_KEY), new User(adminUser));
    }
}
