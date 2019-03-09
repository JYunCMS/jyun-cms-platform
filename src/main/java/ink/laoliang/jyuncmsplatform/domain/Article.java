package ink.laoliang.jyuncmsplatform.domain;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
public class Article extends _BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authorId;

    private String abstracts;

    @Column(columnDefinition = "longtext")
    private String content;

    @Column(columnDefinition = "blob")
    private Category category;

    @Column(columnDefinition = "blob")
    private String[] tags;

    @Column(columnDefinition = "blob")
    private Resource[] images;

    @Column(columnDefinition = "blob")
    private Resource[] accessories;

    @Column(nullable = false)
    private String status;

    private Boolean beDelete;

    public Article() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Resource[] getImages() {
        return images;
    }

    public void setImages(Resource[] images) {
        this.images = images;
    }

    public Resource[] getAccessories() {
        return accessories;
    }

    public void setAccessories(Resource[] accessories) {
        this.accessories = accessories;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getBeDelete() {
        return beDelete;
    }

    public void setBeDelete(Boolean beDelete) {
        this.beDelete = beDelete;
    }
}
