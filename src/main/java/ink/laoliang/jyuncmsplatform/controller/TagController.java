package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.Tag;
import ink.laoliang.jyuncmsplatform.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getTags() {
        return tagService.getTags();
    }

    @PostMapping
    public List<Tag> createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @DeleteMapping
    public List<Tag> deleteTag(@RequestAttribute String USER_ROLE,
                               @RequestParam String name) {
        return tagService.deleteTag(USER_ROLE, name);
    }
}
