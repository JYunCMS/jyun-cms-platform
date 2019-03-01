package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.Tag;
import ink.laoliang.jyuncmsplatform.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getTags() {
        return tagService.getTags();
    }

    @PostMapping
    public List<Tag> createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @DeleteMapping
    public List<Tag> deleteTag(@RequestParam String name) {
        return tagService.deleteTag(name);
    }
}
