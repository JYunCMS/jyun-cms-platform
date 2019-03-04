package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Resource;
import ink.laoliang.jyuncmsplatform.domain.response.FilterConditions;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResourceService {
    Resource upload(MultipartFile file);

    List<Resource> getResources();

    List<Resource> deleteResource(String filePath);

    FilterConditions getFilterConditions();

    List<Resource> getByConditions(String date, String type);
}
