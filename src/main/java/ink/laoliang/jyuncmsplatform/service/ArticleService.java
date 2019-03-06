package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getArticles();

    Article newArticle(Article article);
}
