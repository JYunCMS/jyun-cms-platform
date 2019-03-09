package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.*;
import ink.laoliang.jyuncmsplatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public Article updateArticle(Article article) {
        Article oldArticle = articleRepository.findById(article.getId()).orElse(null);

        // 更新 Resource 表 referenceCount 字段
        for (Resource imageResource : article.getImages()) {
            imageResource.setReferenceCount(1);
            resourceRepository.save(imageResource);
        }

        // 对比新旧文章 images 引用列表，新 article 对象只有最新添加的图片列表，
        // 所以需要将旧 article 中还在用的图片引用添加进新的，不再用的对应资源计数 -1
        for (Resource imageResource : oldArticle.getImages()) {
            if (article.getContent().contains(imageResource.getLocation())) {
                List<Resource> tempImageResourceList = Arrays.asList(article.getImages());
                List<Resource> imageResourceList = new ArrayList<>(tempImageResourceList);
                imageResourceList.add(imageResource);
                article.setImages(imageResourceList.toArray(new Resource[0]));
            } else {
                imageResource.setReferenceCount(imageResource.getReferenceCount() - 1);
                resourceRepository.save(imageResource);
            }
        }

        // 如果分类有更新，就更新对应分类的文章计数
        if (!article.getCategory().getUrlAlias().equals(oldArticle.getCategory().getUrlAlias())) {
            Category category = article.getCategory();
            category.setArticleCount(category.getArticleCount() + 1);
            categoryRepository.save(category);
            category = oldArticle.getCategory();
            category.setArticleCount(category.getArticleCount() - 1);
            categoryRepository.save(category);
        }

        // 处理标签变动
        List<String> newArticleTagList = Arrays.asList(article.getTags());
        List<String> oldArticleTagList = Arrays.asList(oldArticle.getTags());
        for (String newTagName : newArticleTagList) {
            if (!oldArticleTagList.contains(newTagName)) {
                Tag tag = tagRepository.findByName(newTagName);
                if (tag != null) {
                    tag.setArticleCount(tag.getArticleCount() + 1);
                    tagRepository.save(tag);
                } else {
                    tagRepository.save(new Tag(newTagName, 1));
                }
            }
        }
        for (String oldTagName : oldArticleTagList) {
            if (!newArticleTagList.contains(oldTagName)) {
                Tag tag = tagRepository.findByName(oldTagName);
                if (tag != null) {
                    tag.setArticleCount(tag.getArticleCount() - 1);
                    tagRepository.save(tag);
                }
            }
        }

        return articleRepository.save(article);
    }
}
