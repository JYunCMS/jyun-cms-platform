package ink.laoliang.jyuncmsplatform.domain;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
public class ArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Integer id;

    @Column(nullable = false, columnDefinition = "bigint unsigned")
    private Integer articleId;

    @Column(nullable = false)
    private String tagName;

    public ArticleTag() {
    }

    public ArticleTag(Integer articleId, String tagName) {
        this.articleId = articleId;
        this.tagName = tagName;
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
