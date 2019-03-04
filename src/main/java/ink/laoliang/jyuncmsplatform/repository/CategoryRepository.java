package ink.laoliang.jyuncmsplatform.repository;

import ink.laoliang.jyuncmsplatform.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Integer countByNodeLevelAndParentNodeUrlAlias(Integer nodeLevel, String parentUrlAlias);

    Category findByParentNodeUrlAliasAndSequence(String parentNodeUrlAlias, Integer sequence);

    List<Category> findAllByParentNodeUrlAlias(String parentNodeUrlAlias);
}
