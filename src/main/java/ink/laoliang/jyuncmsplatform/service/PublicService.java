package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Article;
import ink.laoliang.jyuncmsplatform.domain.Category;
import ink.laoliang.jyuncmsplatform.domain.Options;

import java.util.List;

public interface PublicService {

    List<Category> getCategories();

    List<Article> getArticlesByCategory(String categoryUrlAlias, Integer page, Integer size);

    Article getArticleById(Integer id);

    List<Options> getOptions();
}
