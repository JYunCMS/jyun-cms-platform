package ink.laoliang.jyuncmsplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HelloController {

    @GetMapping
    public String sayHello(){
        return "Welcome to JYunCMS!";
    }
}
