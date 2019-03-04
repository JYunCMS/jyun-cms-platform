package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {
    Resource upload(MultipartFile file);
}
