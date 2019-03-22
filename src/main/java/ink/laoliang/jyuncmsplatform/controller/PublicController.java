package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.Article;
import ink.laoliang.jyuncmsplatform.domain.Category;
import ink.laoliang.jyuncmsplatform.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/public")
public class PublicController {

    private final PublicService publicService;

    @Autowired
    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping(value = "/categories")
    public List<Category> getCategories() {
        return publicService.getCategories();
    }

    @GetMapping(value = "/articles-by-category")
    public List<Article> getArticlesByCategory(@RequestParam String categoryUrlAlias,
                                               @RequestParam Integer page,
                                               @RequestParam Integer size) {
        return publicService.getArticlesByCategory(categoryUrlAlias, page, size);
    }
}
