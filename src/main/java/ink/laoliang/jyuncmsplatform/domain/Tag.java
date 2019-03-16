package ink.laoliang.jyuncmsplatform.domain;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Component
public class Tag extends _BaseEntity implements Serializable {

    @Id
    @Column(columnDefinition = "char(20)")
    private String name;

    @Column(nullable = false, columnDefinition = "bigint unsigned")
    private Integer articleCount;

    public Tag() {
    }

    public Tag(String name, Integer articleCount) {
        this.name = name;
        this.articleCount = articleCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }
}
