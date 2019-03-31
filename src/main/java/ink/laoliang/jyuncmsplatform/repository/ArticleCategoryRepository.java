package ink.laoliang.jyuncmsplatform.repository;

import ink.laoliang.jyuncmsplatform.domain.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Integer> {

    ArticleCategory findByArticleIdAndCategoryUrlAlias(Integer articleId, String categoryUrlAlias);

    @Transactional
    void deleteArticleCategoryByArticleIdAndCategoryUrlAlias(Integer articleId, String categoryUrlAlias);

    @Transactional
    void deleteAllByCategoryUrlAlias(String categoryUrlAlias);
}
