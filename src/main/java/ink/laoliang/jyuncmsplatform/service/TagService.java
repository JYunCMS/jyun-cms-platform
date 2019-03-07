package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.ArticleTag;
import ink.laoliang.jyuncmsplatform.domain.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getTags();

    List<Tag> createTag(Tag tag);

    Tag updateTag(Tag tag);

    List<Tag> deleteTag(String name);

    ArticleTag addArticleBind(ArticleTag articleTag);
}
