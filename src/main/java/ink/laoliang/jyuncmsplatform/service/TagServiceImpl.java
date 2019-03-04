package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Article;
import ink.laoliang.jyuncmsplatform.domain.ArticleTag;
import ink.laoliang.jyuncmsplatform.domain.Tag;
import ink.laoliang.jyuncmsplatform.exception.IllegalParameterException;
import ink.laoliang.jyuncmsplatform.repository.ArticleRepository;
import ink.laoliang.jyuncmsplatform.repository.ArticleTagRepository;
import ink.laoliang.jyuncmsplatform.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private Sort ORDER_BY_CREATED_AT = new Sort(Sort.Direction.DESC, "createdAt");

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Autowired
    private ArticleRepository articleRepository;

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
    public List<Tag> deleteTag(String name) {
        List<ArticleTag> articleTagList = articleTagRepository.findAllByTagName(name);
        if (articleTagList != null && articleTagList.size() != 0) {
            for (ArticleTag articleTag : articleTagList) {
                // 更新文章 tags 字段
                Article article = articleRepository.getOne(articleTag.getArticleId());
                List<String> tags = Arrays.asList(article.getTags());
                if (!tags.remove(name)) {
                    throw new IllegalParameterException("【非法参数异常】- 标签 " + name + " 不存在！");
                }
                article.setTags(tags.toArray(new String[tags.size()]));
                articleRepository.save(article);

                // 删除 article_tag 表对应行
                articleTagRepository.deleteByArticleIdAndTagName(articleTag.getArticleId(), name);
            }
        }

        // 删除标签
        tagRepository.deleteById(name);
        return tagRepository.findAll(ORDER_BY_CREATED_AT);
    }
}
