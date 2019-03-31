package ink.laoliang.jyuncmsplatform.domain;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
public class ArticleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Integer id;

    @Column(nullable = false, columnDefinition = "bigint unsigned")
    private Integer articleId;

    @Column(nullable = false)
    private String categoryUrlAlias;

    public ArticleCategory() {
    }

    public ArticleCategory(Integer articleId, String categoryUrlAlias) {
        this.articleId = articleId;
        this.categoryUrlAlias = categoryUrlAlias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getCategoryUrlAlias() {
        return categoryUrlAlias;
    }

    public void setCategoryUrlAlias(String categoryUrlAlias) {
        this.categoryUrlAlias = categoryUrlAlias;
    }
}
