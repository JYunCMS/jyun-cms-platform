package ink.laoliang.jyuncmsplatform.config;

public class ArticleStatus {

    /**
     * 对应数据库中 article 表的 status 字段
     */
    public static final String PUBLISHED = "已发布";
    public static final String PENDING_REVIEW = "待审核";
    public static final String DRAFT = "草稿";

    /**
     * 仅供前端条件查询使用，不可用于存库
     */
    public static final String ALL = "全部";
    public static final String RECYCLE_BIN = "回收站";
}
