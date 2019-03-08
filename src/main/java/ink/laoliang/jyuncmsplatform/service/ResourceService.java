package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Resource;
import ink.laoliang.jyuncmsplatform.domain.response.FilterConditions;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResourceService {
    List<Resource> getResources();

    Resource upload(MultipartFile file);

    Resource updateResource(Resource resource);

    List<Resource> deleteResource(String location);

    FilterConditions getFilterConditions();

    List<Resource> getByConditions(String date, String type);
}
