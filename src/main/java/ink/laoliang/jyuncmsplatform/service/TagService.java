package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getTags();

    List<Tag> createTag(Tag tag);

    List<Tag> deleteTag(String name);
}
