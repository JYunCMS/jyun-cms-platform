package ink.laoliang.jyuncmsplatform.repository;

import ink.laoliang.jyuncmsplatform.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
