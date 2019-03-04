package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();

    List<Category> createCategory(Category category);

    List<Category> updateCategory(Category category);

    List<Category> deleteCategory(String urlAlias);

    Category getCategory(String urlAlias);

    Integer getCount(Integer nodeLevel, String parentUrlAlias);

    List<Category> moveUpNode(String urlAlias);

    List<Category> moveDownNode(String urlAlias);
}
