package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.Resource;
import ink.laoliang.jyuncmsplatform.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping
    public Resource upload(@RequestParam MultipartFile file){
        return resourceService.upload(file);
    }
}
