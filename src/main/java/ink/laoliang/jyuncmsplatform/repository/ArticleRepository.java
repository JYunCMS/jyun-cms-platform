package ink.laoliang.jyuncmsplatform.repository;

import ink.laoliang.jyuncmsplatform.domain.Article;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    long countByBeDelete(Boolean beDelete);

    long countByBeDeleteAndStatus(Boolean beDelete, String status);

    List<Article> findAllByBeDelete(Boolean beDelete, Sort sort);

    List<Article> findAllByBeDeleteAndStatus(Boolean beDelete, String status, Sort sort);

    @Query(value = "select new Article(article) from Article article where article.createdAt between :startDate and :endDate and article.status like :status and article.beDelete = :beDelete order by article.createdAt desc")
    List<Article> findAllByConditions(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("status") String status, @Param("beDelete") Boolean beDelete);
}
