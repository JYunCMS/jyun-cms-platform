package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.ArticleTag;
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

    @PutMapping
    public Tag updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }

    @DeleteMapping
    public List<Tag> deleteTag(@RequestParam String name) {
        return tagService.deleteTag(name);
    }

    @PostMapping(value = "/article-bind")
    public ArticleTag addArticleBind(@RequestBody ArticleTag articleTag){
        return tagService.addArticleBind(articleTag);
    }
}
