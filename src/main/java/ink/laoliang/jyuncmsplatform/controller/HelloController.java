package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.request.InitSystemInfo;
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
    public Boolean alreadyInitSystem() {
        return helloService.alreadyInitSystem();
    }

    @PostMapping
    public LoginUserInfo initSystem(@RequestBody InitSystemInfo initSystemInfo) {
        return helloService.initSystem(initSystemInfo);
    }
}
