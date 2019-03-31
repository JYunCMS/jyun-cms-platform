package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.Article;
import ink.laoliang.jyuncmsplatform.domain.response.ArticleFilterConditions;
import ink.laoliang.jyuncmsplatform.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getArticles() {
        return articleService.getArticles();
    }

    @PostMapping
    public Article newArticle(@RequestAttribute String USER_ROLE,
                              @RequestBody Article article) {
        return articleService.newArticle(USER_ROLE, article);
    }

    @PutMapping
    public Article updateArticle(@RequestAttribute String USER_ROLE,
                                 @RequestBody Article article) {
        return articleService.updateArticle(USER_ROLE, article);
    }

    @DeleteMapping
    public void deleteArticle(@RequestAttribute String USER_ROLE,
                              @RequestParam Integer articleId) {
        articleService.deleteArticle(USER_ROLE, articleId);
    }

    @GetMapping(value = "/filter-conditions")
    public ArticleFilterConditions getFilterConditions() {
        return articleService.getFilterConditions();
    }

    @GetMapping(value = "/by-status")
    public List<Article> getArticlesByStatus(@RequestParam String status) {
        return articleService.getArticlesByStatus(status);
    }

    @GetMapping(value = "/by-conditions")
    public List<Article> getArticlesByConditions(@RequestParam String status,
                                                 @RequestParam String selectedDate,
                                                 @RequestParam String selectedCategory,
                                                 @RequestParam String selectedTag) {
        return articleService.getArticlesByConditions(status, selectedDate, selectedCategory, selectedTag);
    }

    @PutMapping(value = "/move-to-recycle-bin")
    public Article moveToRecycleBin(@RequestAttribute String USER_ROLE,
                                    @RequestParam Boolean beDelete,
                                    @RequestBody Article article) {
        return articleService.moveToRecycleBin(USER_ROLE, beDelete, article);
    }
}
