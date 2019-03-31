package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.request.InitSystemInfo;
import ink.laoliang.jyuncmsplatform.domain.response.LoginUserInfo;

public interface HelloService {

    Boolean alreadyInitSystem();

    LoginUserInfo initSystem(InitSystemInfo initSystemInfo);
}
