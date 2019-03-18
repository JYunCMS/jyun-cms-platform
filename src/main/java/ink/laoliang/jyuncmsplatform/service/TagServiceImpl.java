package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Article;
import ink.laoliang.jyuncmsplatform.domain.ArticleTag;
import ink.laoliang.jyuncmsplatform.domain.Tag;
import ink.laoliang.jyuncmsplatform.exception.IllegalParameterException;
import ink.laoliang.jyuncmsplatform.exception.UserRolePermissionException;
import ink.laoliang.jyuncmsplatform.repository.ArticleRepository;
import ink.laoliang.jyuncmsplatform.repository.ArticleTagRepository;
import ink.laoliang.jyuncmsplatform.repository.TagRepository;
import ink.laoliang.jyuncmsplatform.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final Sort ORDER_BY_CREATED_AT = new Sort(Sort.Direction.DESC, "createdAt");

    private final TagRepository tagRepository;

    private final ArticleTagRepository articleTagRepository;

    private final ArticleRepository articleRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, ArticleTagRepository articleTagRepository, ArticleRepository articleRepository) {
        this.tagRepository = tagRepository;
        this.articleTagRepository = articleTagRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAll(ORDER_BY_CREATED_AT);
    }

    @Override
    public List<Tag> createTag(Tag tag) {
        if (tagRepository.existsById(tag.getName())) {
            throw new IllegalParameterException("【非法参数异常】- 标签 " + tag.getName() + " 已存在！");
        }

        tagRepository.save(tag);
        return tagRepository.findAll(ORDER_BY_CREATED_AT);
    }

    @Override
    public List<Tag> deleteTag(String USER_ROLE, String name) {
        // 验证用户角色权限
        if (UserRole.getUserRoleLevel(USER_ROLE) <= 1) {
            throw new UserRolePermissionException("【用户角色权限异常】- 当前用户角色等级没有删除标签的权限！");
        }

        List<ArticleTag> articleTagList = articleTagRepository.findAllByTagName(name);
        if (articleTagList != null && articleTagList.size() != 0) {
            for (ArticleTag articleTag : articleTagList) {
                // 更新文章 tags 字段
                Article article = articleRepository.findById(articleTag.getArticleId()).orElse(null);
                List<String> tempTags = Arrays.asList(article.getTags());
                List<String> tags = new ArrayList<>(tempTags);
                tags.remove(name);
                article.setTags(tags.toArray(new String[0]));
                articleRepository.save(article);

                // 删除 article_tag 表对应行
                articleTagRepository.deleteArticleTagByArticleIdAndTagName(articleTag.getArticleId(), name);
            }
        }

        // 删除标签
        tagRepository.deleteById(name);
        return tagRepository.findAll(ORDER_BY_CREATED_AT);
    }
}
