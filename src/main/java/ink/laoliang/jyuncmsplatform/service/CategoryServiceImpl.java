package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Category;
import ink.laoliang.jyuncmsplatform.exception.CategoryUpdateException;
import ink.laoliang.jyuncmsplatform.exception.UserRolePermissionException;
import ink.laoliang.jyuncmsplatform.repository.CategoryRepository;
import ink.laoliang.jyuncmsplatform.util.UserRole;
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

    private Sort ORDER_BY_SEQUENCE = new Sort(Sort.Direction.ASC, "sequence");

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll(ORDER_BY_SEQUENCE);
    }

    @Override
    public List<Category> createCategory(String USER_ROLE, Category category) {
        // 验证用户角色权限
        if (UserRole.getUserRoleLevel(USER_ROLE) <= 1) {
            throw new UserRolePermissionException("【用户角色权限异常】- 当前用户角色等级没有创建新分类目录的权限！");
        }

        categoryRepository.save(category);
        return categoryRepository.findAll(ORDER_BY_SEQUENCE);
    }

    @Override
    public List<Category> updateCategory(String USER_ROLE, Category category) {
        // 验证用户角色权限
        if (UserRole.getUserRoleLevel(USER_ROLE) <= 1) {
            throw new UserRolePermissionException("【用户角色权限异常】- 当前用户角色等级没有更新分类目录的权限！");
        }

        Category categoryModel = categoryRepository.findByUrlAlias(category.getUrlAlias());

        try {
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
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new CategoryUpdateException("【分类更新异常】", e);
        }

        categoryRepository.save(categoryModel);
        return categoryRepository.findAll(ORDER_BY_SEQUENCE);
    }

    @Override
    public List<Category> deleteCategory(String USER_ROLE, String urlAlias) {
        // 验证用户角色权限
        if (UserRole.getUserRoleLevel(USER_ROLE) <= 1) {
            throw new UserRolePermissionException("【用户角色权限异常】- 当前用户角色等级没有删除分类目录的权限！");
        }

        Category category = categoryRepository.findByUrlAlias(urlAlias);

        // 是否是叶子节点
        if (category.getBeLeaf()) {
            // 是叶子结点
            // 移动该节点文章到回收站 urlAlias
            ///////////////////////
            ///////////////////////
            ///////////////////////
            ///////////////////////
            ///////////////////////

            handleDeleteNode(category);

            // 刷新所有节点文章数
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////

        } else {
            // 不是叶子结点
            // 遍历移动该节点下所有叶子节点文章到回收站 urlAliasList
            List<String> leafUrlAliasList = new ArrayList<>();
            getAllLeafUrlAlias(category, leafUrlAliasList);
            //////////////////////////////////////////
            //////////////////////////////////////////
            //////////////////////////////////////////
            //////////////////////////////////////////
            //////////////////////////////////////////
            //////////////////////////////////////////
            //////////////////////////////////////////
            //////////////////////////////////////////
            //////////////////////////////////////////
            //////////////////////////////////////////

            handleDeleteNode(category);

            // 删除该节点的所有子节点
            deleteAllSubNode(category);

            // 刷新所有节点文章数
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
            ////////////////////
        }

        return categoryRepository.findAll(ORDER_BY_SEQUENCE);
    }

    @Override
    public Category getCategory(String urlAlias) {
        return categoryRepository.findByUrlAlias(urlAlias);
    }

    @Override
    public Integer getCount(Integer nodeLevel, String parentUrlAlias) {
        return categoryRepository.countByNodeLevelAndParentNodeUrlAlias(nodeLevel, parentUrlAlias);
    }

    @Override
    public List<Category> moveUpNode(String USER_ROLE, String urlAlias) {
        // 验证用户角色权限
        if (UserRole.getUserRoleLevel(USER_ROLE) <= 1) {
            throw new UserRolePermissionException("【用户角色权限异常】- 当前用户角色等级没有移动分类目录位置的权限！");
        }

        Category currentCategory = categoryRepository.findByUrlAlias(urlAlias);
        Category frontCategory = categoryRepository.findByParentNodeUrlAliasAndSequence(currentCategory.getParentNodeUrlAlias(), currentCategory.getSequence() - 1);

        currentCategory.setSequence(currentCategory.getSequence() - 1);
        frontCategory.setSequence(frontCategory.getSequence() + 1);

        categoryRepository.save(currentCategory);
        categoryRepository.save(frontCategory);

        return categoryRepository.findAll(ORDER_BY_SEQUENCE);
    }

    @Override
    public List<Category> moveDownNode(String USER_ROLE, String urlAlias) {
        // 验证用户角色权限
        if (UserRole.getUserRoleLevel(USER_ROLE) <= 1) {
            throw new UserRolePermissionException("【用户角色权限异常】- 当前用户角色等级没有移动分类目录位置的权限！");
        }

        Category currentCategory = categoryRepository.findByUrlAlias(urlAlias);
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
            Category parentCategory = categoryRepository.findByUrlAlias(category.getParentNodeUrlAlias());
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
            if (!childrenCategory.getBeLeaf()) {
                deleteAllSubNode(childrenCategory);
            }
        }
    }
}
