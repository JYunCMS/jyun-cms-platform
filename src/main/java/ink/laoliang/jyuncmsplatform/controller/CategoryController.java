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
    public List<Category> createCategory(@RequestAttribute String USER_ROLE,
                                         @RequestBody Category category) {
        return categoryService.createCategory(USER_ROLE, category);
    }

    @PutMapping
    public List<Category> updateCategory(@RequestAttribute String USER_ROLE,
                                         @RequestBody Category category) {
        return categoryService.updateCategory(USER_ROLE, category);
    }

    @DeleteMapping
    public List<Category> deleteCategory(@RequestAttribute String USER_ROLE,
                                         @RequestParam String urlAlias) {
        return categoryService.deleteCategory(USER_ROLE, urlAlias);
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
    public List<Category> moveUpNode(@RequestAttribute String USER_ROLE,
                                     @RequestParam String urlAlias) {
        return categoryService.moveUpNode(USER_ROLE, urlAlias);
    }

    @PatchMapping(value = "/move-down-node")
    public List<Category> moveDownNode(@RequestAttribute String USER_ROLE,
                                       @RequestParam String urlAlias) {
        return categoryService.moveDownNode(USER_ROLE, urlAlias);
    }
}
