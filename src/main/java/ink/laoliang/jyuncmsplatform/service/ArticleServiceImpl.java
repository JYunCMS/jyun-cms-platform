package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.*;
import ink.laoliang.jyuncmsplatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final Sort ORDER_BY_CREATED_AT = new Sort(Sort.Direction.DESC, "createdAt");

    private final ArticleRepository articleRepository;

    private final CategoryRepository categoryRepository;

    private final TagRepository tagRepository;

    private final ArticleTagRepository articleTagRepository;

    private final ResourceRepository resourceRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryRepository categoryRepository, TagRepository tagRepository, ArticleTagRepository articleTagRepository, ResourceRepository resourceRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.articleTagRepository = articleTagRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public List<Article> getArticles() {
        return articleRepository.findAll(ORDER_BY_CREATED_AT);
    }

    @Override
    public Article newArticle(Article article) {
        Article articleResult = articleRepository.save(article);

        // 文章保存成功后……
        // 1、更新 Category 表 articleCount 字段
        Category category = categoryRepository.findByUrlAlias(articleResult.getCategory().getUrlAlias());
        category.setArticleCount(category.getArticleCount() + 1);
        categoryRepository.save(category);

        // 2、更新 Tag 表 articleCount 字段，并添加 文章-标签 绑定到 ArticleTag 表
        if (articleResult.getTags().length != 0) {
            for (String tagName : articleResult.getTags()) {
                // 添加新标签， 或更新已存在标签 articleCount
                Tag tag = tagRepository.findByName(tagName);
                if (tag == null) {
                    tagRepository.save(new Tag(tagName, 1));
                } else {
                    tag.setArticleCount(tag.getArticleCount() + 1);
                    tagRepository.save(tag);
                }

                // 添加 文章-标签 绑定到 ArticleTag 表
                articleTagRepository.save(new ArticleTag(articleResult.getId(), tagName));

            }
        }

        // 3、更新 Resource 表 referenceCount 字段
        for (Resource imageResource : articleResult.getImages()) {
            imageResource.setReferenceCount(1);
            resourceRepository.save(imageResource);
        }
        for (Resource accessoryResource : articleResult.getAccessories()) {
            accessoryResource.setReferenceCount(1);
            resourceRepository.save(accessoryResource);
        }

        return articleResult;
    }
}
