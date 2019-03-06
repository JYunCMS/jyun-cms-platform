package ink.laoliang.jyuncmsplatform.domain;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Component
public class Resource extends _BaseEntity implements Serializable {

    @Id
    private String filePath;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String storageFilename;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private String fileSize;

    @Column(nullable = false, columnDefinition = "bigint unsigned")
    private Integer referenceCount;

    public Resource() {
    }

    public Resource(Resource resource) {
        this.filePath = resource.filePath;
        this.originalFilename = resource.originalFilename;
        this.storageFilename = resource.storageFilename;
        this.fileType = resource.fileType;
        this.fileSize = resource.fileSize;
        this.referenceCount = resource.referenceCount;
        this.createdAt = resource.createdAt;
        this.updatedAt = resource.updatedAt;
    }

    public Resource(String filePath, String originalFilename, String storageFilename, String fileType, String fileSize, Integer referenceCount) {
        this.filePath = filePath;
        this.originalFilename = originalFilename;
        this.storageFilename = storageFilename;
        this.fileType = fileType;
        this.fileSize = fileSize;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(Integer referenceCount) {
        this.referenceCount = referenceCount;
    }
}
