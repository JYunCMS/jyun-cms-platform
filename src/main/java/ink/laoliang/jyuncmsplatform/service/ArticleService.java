package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Article;
import ink.laoliang.jyuncmsplatform.domain.response.ArticleFilterConditions;

import java.util.List;

public interface ArticleService {
    List<Article> getArticles();

    Article newArticle(Article article);

    Article updateArticle(Article article);

    void deleteArticle(Integer articleId);

    ArticleFilterConditions getFilterConditions();

    List<Article> getArticlesByStatus(String status);

    List<Article> getArticlesByConditions(String status, String selectedDate, String selectedCategory, String selectedTag);

    Article moveToRecycleBin(Boolean beDelete, Article article);
}
