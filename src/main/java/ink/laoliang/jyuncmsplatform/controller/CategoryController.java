package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.Category;
import ink.laoliang.jyuncmsplatform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    public List<Category> createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping
    public List<Category> updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping
    public List<Category> deleteCategory(@RequestParam String urlAlias) {
        return categoryService.deleteCategory(urlAlias);
    }

    @GetMapping(value = "/category")
    public Category getCategory(@RequestParam String urlAlias) {
        return categoryService.getCategory(urlAlias);
    }

    @GetMapping(value = "/count")
    public Integer getCount(@RequestParam Integer nodeLevel,
                            @RequestParam String parentUrlAlias) {
        return categoryService.getCount(nodeLevel, parentUrlAlias);
    }

    @PatchMapping(value = "/move-up-node")
    public List<Category> moveUpNode(@RequestParam String urlAlias) {
        return categoryService.moveUpNode(urlAlias);
    }

    @PatchMapping(value = "/move-down-node")
    public List<Category> moveDownNode(@RequestParam String urlAlias) {
        return categoryService.moveDownNode(urlAlias);
    }
}
