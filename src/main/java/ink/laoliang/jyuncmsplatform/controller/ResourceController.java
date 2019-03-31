package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.Resource;
import ink.laoliang.jyuncmsplatform.domain.response.ResourceFilterConditions;
import ink.laoliang.jyuncmsplatform.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<Resource> getResources() {
        return resourceService.getResources();
    }

    @DeleteMapping
    public List<Resource> deleteResource(@RequestAttribute String USER_ROLE,
                                         @RequestParam String location) {
        return resourceService.deleteResource(USER_ROLE, location);
    }

    @GetMapping(value = "/filter-conditions")
    public ResourceFilterConditions getFilterConditions() {
        return resourceService.getFilterConditions();
    }

    @GetMapping(value = "/by-conditions")
    public List<Resource> getByConditions(@RequestParam String date,
                                          @RequestParam String type) {
        return resourceService.getByConditions(date, type);
    }
}
