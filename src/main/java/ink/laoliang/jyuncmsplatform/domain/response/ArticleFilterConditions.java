package ink.laoliang.jyuncmsplatform.domain.response;

import ink.laoliang.jyuncmsplatform.domain.Category;
import ink.laoliang.jyuncmsplatform.domain.Tag;

import java.util.List;

public class ArticleFilterConditions {

    private List<String> dateList;

    private List<Category> categoryList;

    private List<Tag> tagList;

    private long allExcludeRecycleBinCount;

    private long releaseCount;

    private long pendingReviewCount;

    private long draftCount;

    private long recycleBinCount;

    public ArticleFilterConditions(List<String> dateList, List<Category> categoryList, List<Tag> tagList, long allExcludeRecycleBinCount, long releaseCount, long pendingReviewCount, long draftCount, long recycleBinCount) {
        this.dateList = dateList;
        this.categoryList = categoryList;
        this.tagList = tagList;
        this.allExcludeRecycleBinCount = allExcludeRecycleBinCount;
        this.releaseCount = releaseCount;
        this.pendingReviewCount = pendingReviewCount;
        this.draftCount = draftCount;
        this.recycleBinCount = recycleBinCount;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public long getAllExcludeRecycleBinCount() {
        return allExcludeRecycleBinCount;
    }

    public void setAllExcludeRecycleBinCount(long allExcludeRecycleBinCount) {
        this.allExcludeRecycleBinCount = allExcludeRecycleBinCount;
    }

    public long getReleaseCount() {
        return releaseCount;
    }

    public void setReleaseCount(long releaseCount) {
        this.releaseCount = releaseCount;
    }

    public long getPendingReviewCount() {
        return pendingReviewCount;
    }

    public void setPendingReviewCount(long pendingReviewCount) {
        this.pendingReviewCount = pendingReviewCount;
    }

    public long getDraftCount() {
        return draftCount;
    }

    public void setDraftCount(long draftCount) {
        this.draftCount = draftCount;
    }

    public long getRecycleBinCount() {
        return recycleBinCount;
    }

    public void setRecycleBinCount(long recycleBinCount) {
        this.recycleBinCount = recycleBinCount;
    }
}
