package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Article;
import ink.laoliang.jyuncmsplatform.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final Sort ORDER_BY_CREATED_AT = new Sort(Sort.Direction.DESC, "createdAt");

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> getArticles() {
        return articleRepository.findAll(ORDER_BY_CREATED_AT);
    }

    @Override
    public Article newArticle(Article article) {
        return articleRepository.save(article);
    }
}
