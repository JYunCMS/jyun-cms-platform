package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.Category;
import ink.laoliang.jyuncmsplatform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    public List<Category> createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping
    public List<Category> updateCategory(@RequestBody Category category) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping
    public List<Category> deleteCategory(@RequestParam String urlAlias) {
        return categoryService.deleteCategory(urlAlias);
    }

    @GetMapping(value = "/categoryById")
    public Category getCategoryById(@RequestParam String urlAlias) {
        return categoryService.getCategoryById(urlAlias);
    }

    @GetMapping(value = "/countByLevelAndParentUrlAlias")
    public Integer getCountByLevel(@RequestParam Integer nodeLevel,
                                   @RequestParam String parentUrlAlias) {
        return categoryService.getCountByLevelAndParentUrlAlias(nodeLevel, parentUrlAlias);
    }

    @PatchMapping(value = "/moveUpNode")
    public List<Category> moveUpNode(@RequestParam String urlAlias) {
        return categoryService.moveUpNode(urlAlias);
    }

    @PatchMapping(value = "/moveDownNode")
    public List<Category> moveDownNode(@RequestParam String urlAlias) {
        return categoryService.moveDownNode(urlAlias);
    }
}
