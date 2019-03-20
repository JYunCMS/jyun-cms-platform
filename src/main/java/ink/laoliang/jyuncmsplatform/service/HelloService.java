package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.request.InitJYunCmsInfo;
import ink.laoliang.jyuncmsplatform.domain.response.LoginUserInfo;

public interface HelloService {

    Boolean isFirstBoot();

    LoginUserInfo initJYunCms(InitJYunCmsInfo initJYunCmsInfo);
}
