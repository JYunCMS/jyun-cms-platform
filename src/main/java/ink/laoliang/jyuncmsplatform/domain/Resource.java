package ink.laoliang.jyuncmsplatform.domain;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Component
public class Resource extends _BaseEntity {

    @Id
    private String filePath;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String storageFilename;

    @Column(nullable = false, columnDefinition = "bigint unsigned")
    private Integer referenceCount;

    public Resource() {
    }

    public Resource(String filePath, String originalFilename, String storageFilename, Integer referenceCount) {
        this.filePath = filePath;
        this.originalFilename = originalFilename;
        this.storageFilename = storageFilename;
        this.referenceCount = referenceCount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getStorageFilename() {
        return storageFilename;
    }

    public void setStorageFilename(String storageFilename) {
        this.storageFilename = storageFilename;
    }

    public Integer getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(Integer referenceCount) {
        this.referenceCount = referenceCount;
    }
}
