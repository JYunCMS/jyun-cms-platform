package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.request.InitJYunCmsInfo;
import ink.laoliang.jyuncmsplatform.domain.response.LoginUserInfo;
import ink.laoliang.jyuncmsplatform.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    private final HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping
    public Boolean isFirstBoot() {
        return helloService.isFirstBoot();
    }

    @PostMapping
    public LoginUserInfo initJYunCms(@RequestBody InitJYunCmsInfo initJYunCmsInfo) {
        return helloService.initJYunCms(initJYunCmsInfo);
    }
}
