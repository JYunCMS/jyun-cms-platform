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

    private String[] tags;

    private String[] resources;

    public Article() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getResources() {
        return resources;
    }

    public void setResources(String[] resources) {
        this.resources = resources;
    }
}
