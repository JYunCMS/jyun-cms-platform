package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();

    List<Category> createCategory(String USER_ROLE, Category category);

    List<Category> updateCategory(String USER_ROLE, Category category);

    List<Category> deleteCategory(String USER_ROLE, String urlAlias);

    Category getCategory(String urlAlias);

    Integer getCount(Integer nodeLevel, String parentUrlAlias);

    List<Category> moveUpNode(String USER_ROLE, String urlAlias);

    List<Category> moveDownNode(String USER_ROLE, String urlAlias);
}
