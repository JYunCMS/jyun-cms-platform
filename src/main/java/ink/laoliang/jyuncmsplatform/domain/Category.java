package ink.laoliang.jyuncmsplatform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Component
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Category extends _BaseEntity {

    @Id
    private String urlAlias;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Boolean beLeaf;

    @Column(nullable = false)
    private Integer nodeLevel;

    private String parentNodeUrlAlias;

    @Column(nullable = false)
    private Integer sequence;

    @Column(nullable = false)
    private Integer childrenCount;

    @Column(nullable = false, columnDefinition = "bigint unsigned")
    private Integer articleCount;

    @Column(columnDefinition = "longtext")
    private String customPage;

    public Category() {
    }

    public String getUrlAlias() {
        return urlAlias;
    }

    public void setUrlAlias(String urlAlias) {
        this.urlAlias = urlAlias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getBeLeaf() {
        return beLeaf;
    }

    public void setBeLeaf(Boolean beLeaf) {
        this.beLeaf = beLeaf;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public String getParentNodeUrlAlias() {
        return parentNodeUrlAlias;
    }

    public void setParentNodeUrlAlias(String parentNodeUrlAlias) {
        this.parentNodeUrlAlias = parentNodeUrlAlias;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Integer childrenCount) {
        this.childrenCount = childrenCount;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public String getCustomPage() {
        return customPage;
    }

    public void setCustomPage(String customPage) {
        this.customPage = customPage;
    }
}
