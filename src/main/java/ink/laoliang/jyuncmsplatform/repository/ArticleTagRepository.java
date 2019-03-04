package ink.laoliang.jyuncmsplatform.repository;

import ink.laoliang.jyuncmsplatform.domain.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Integer> {

    List<ArticleTag> findAllByTagName(String tagName);

    void deleteByArticleIdAndTagName(Integer articleId, String tagName);
}
