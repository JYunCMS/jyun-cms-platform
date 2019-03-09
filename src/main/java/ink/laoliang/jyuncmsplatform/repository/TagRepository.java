package ink.laoliang.jyuncmsplatform.repository;

import ink.laoliang.jyuncmsplatform.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {

    Tag findByName(String name);
}
