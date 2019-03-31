package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Resource;
import ink.laoliang.jyuncmsplatform.domain.response.ResourceFilterConditions;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResourceService {

    Resource upload(MultipartFile file);

    List<Resource> getResources();

    List<Resource> deleteResource(String USER_ROLE, String location);

    ResourceFilterConditions getFilterConditions();

    List<Resource> getByConditions(String date, String type);
}
