package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Category;
import ink.laoliang.jyuncmsplatform.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private Sort ORDER_BY_SEQUENCE = new Sort(Sort.Direction.ASC, "sequence");

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll(ORDER_BY_SEQUENCE);
    }

    @Override
    public List<Category> createCategory(Category category) {
        categoryRepository.save(category);
        return categoryRepository.findAll(ORDER_BY_SEQUENCE);
    }

    @Override
    public List<Category> updateCategory(Category category) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Category categoryModel = categoryRepository.getOne(category.getUrlAlias());

        // 遍历传入的 category，将不空的（欲更新的）字段更新到 categoryModel 并存库
        for (Field field : category.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(category) != null) {
                String methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Class<?> methodParameterType = field.get(category).getClass();
                Method method = categoryModel.getClass().getMethod(methodName, methodParameterType);
                method.invoke(categoryModel, field.get(category));
            }
        }
        categoryRepository.save(categoryModel);
        return categoryRepository.findAll(ORDER_BY_SEQUENCE);
    }

    @Override
    public void deleteCategory(String urlAlias) {
        Category category = categoryRepository.getOne(urlAlias);

        // 是否是叶子节点
        if (category.getBeLeaf()) {
            // 是叶子结点
            // 移动该节点文章到回收站 urlAlias
            ///////////////////////

            handleDeleteNode(category);

            // 刷新所有节点文章数
            ////////////////////

        } else {
            // 不是叶子结点
            // 遍历移动该节点下所有叶子节点文章到回收站 urlAliasList
            List<String> leafUrlAliasList = new ArrayList<>();
            getAllLeafUrlAlias(category, leafUrlAliasList);
            //////////////////////////////////////////

            handleDeleteNode(category);

            // 删除该节点的所有子节点
            deleteAllSubNode(category);

            // 刷新所有节点文章数
            ////////////////////
        }
    }

    @Override
    public Category getCategoryById(String urlAlias) {
        return categoryRepository.getOne(urlAlias);
    }

    @Override
    public Integer getCountByLevelAndParentUrlAlias(Integer nodeLevel, String parentUrlAlias) {
        return categoryRepository.countByNodeLevelAndParentNodeUrlAlias(nodeLevel, parentUrlAlias);
    }

    @Override
    public List<Category> moveUpNode(String urlAlias) {
        Category currentCategory = categoryRepository.getOne(urlAlias);
        Category frontCategory = categoryRepository.findByParentNodeUrlAliasAndSequence(currentCategory.getParentNodeUrlAlias(), currentCategory.getSequence() - 1);

        currentCategory.setSequence(currentCategory.getSequence() - 1);
        frontCategory.setSequence(frontCategory.getSequence() + 1);

        categoryRepository.save(currentCategory);
        categoryRepository.save(frontCategory);

        return categoryRepository.findAll(ORDER_BY_SEQUENCE);
    }

    @Override
    public List<Category> moveDownNode(String urlAlias) {
        Category currentCategory = categoryRepository.getOne(urlAlias);
        Category backCategory = categoryRepository.findByParentNodeUrlAliasAndSequence(currentCategory.getParentNodeUrlAlias(), currentCategory.getSequence() + 1);

        currentCategory.setSequence(currentCategory.getSequence() + 1);
        backCategory.setSequence(backCategory.getSequence() - 1);

        categoryRepository.save(currentCategory);
        categoryRepository.save(backCategory);

        return categoryRepository.findAll(ORDER_BY_SEQUENCE);
    }

    private void handleDeleteNode(Category category) {
        // 处理该层级节点排序
        int currentSequence = category.getSequence();
        int currentLevelCount = categoryRepository.countByNodeLevelAndParentNodeUrlAlias(category.getNodeLevel(), category.getParentNodeUrlAlias());
        for (int i = currentSequence + 1; i < currentLevelCount + 1; i++) {
            Category tempCategory = categoryRepository.findByParentNodeUrlAliasAndSequence(category.getParentNodeUrlAlias(), i);
            tempCategory.setSequence(i - 1);
            categoryRepository.save(tempCategory);
        }

        // 如果不是根节点
        if (category.getNodeLevel() != 0) {
            // 更新父节点孩子数 childrenCount
            Category parentCategory = categoryRepository.getOne(category.getParentNodeUrlAlias());
            parentCategory.setChildrenCount(parentCategory.getChildrenCount() - 1);

            // 如果这是父节点唯一的子节点
            if (currentLevelCount == 1) {
                // 父节点变为新的叶子结点 beLeaf
                parentCategory.setBeLeaf(true);
            }

            categoryRepository.save(parentCategory);
        }

        // 删除该节点
        categoryRepository.delete(category);
    }

    private void getAllLeafUrlAlias(Category category, List<String> leafUrlAliasList) {
        List<Category> childrenCategoryList = categoryRepository.findAllByParentNodeUrlAlias(category.getUrlAlias());

        for (Category childrenCategory : childrenCategoryList) {
            if (childrenCategory.getBeLeaf()) {
                leafUrlAliasList.add(childrenCategory.getUrlAlias());
            } else {
                getAllLeafUrlAlias(childrenCategory, leafUrlAliasList);
            }
        }
    }

    private void deleteAllSubNode(Category category) {
        List<Category> childrenCategoryList = categoryRepository.findAllByParentNodeUrlAlias(category.getUrlAlias());

        for (Category childrenCategory : childrenCategoryList) {
            categoryRepository.delete(childrenCategory);
            if (childrenCategory.getBeLeaf() == false) {
                deleteAllSubNode(childrenCategory);
            }
        }
    }
}
