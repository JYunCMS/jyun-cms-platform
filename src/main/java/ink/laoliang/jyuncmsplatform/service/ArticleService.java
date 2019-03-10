package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Article;
import ink.laoliang.jyuncmsplatform.domain.response.ArticleFilterConditions;

import java.util.List;

public interface ArticleService {
    List<Article> getArticles();

    Article newArticle(Article article);

    Article updateArticle(Article article);

    ArticleFilterConditions getFilterConditions();

    List<Article> getArticlesByStatus(String status);

    List<Article> getArticlesByConditions(String status, String selectedDate, String selectedCategory, String selectedTag);
}
