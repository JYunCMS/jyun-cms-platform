package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Resource;
import ink.laoliang.jyuncmsplatform.exception.ResourceStorageException;
import ink.laoliang.jyuncmsplatform.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final Sort ORDER_BY_CREATED_AT = new Sort(Sort.Direction.DESC, "createdAt");

    @Value("${upload.dir}")
    private String UPLOAD_DIR;

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Resource upload(MultipartFile file) {
        // 原始文件名
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        Calendar now = Calendar.getInstance();
        // 文件后缀名
        String suffixName = "";
        if (originalFilename.lastIndexOf('.') >= 0) {
            suffixName = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        // 存储文件名，用来在文件系统存储文件，当前系统时间戳生成，具有唯一性
        String storageFilename = now.getTimeInMillis() + suffixName;
        // 上传文件路径，以“年-月”分文件夹，即：每月资源在一个文件夹中
        Path uploadPath = Paths.get(UPLOAD_DIR, now.get(Calendar.YEAR) + "-" + String.format("%02d", now.get(Calendar.MONTH) + 1));
        // 目标文件最终存放路径（即文件在服务器中存放的地址）
        Path targetFilePath = uploadPath.resolve(storageFilename);
        // 文件类型
        String fileType = handleFileTypeField(file.getContentType());

        if (file.isEmpty()) {
            throw new ResourceStorageException("【资源存储异常】- 无法存储空文件 " + originalFilename + " !");
        }
        if (originalFilename.contains("..")) {
            throw new ResourceStorageException("【资源存储异常】- 无法存储当前目录外的相对路径的文件 " + originalFilename + " !");
        }

        try {
            // 创建文件夹（如果已存在则直接返回）
            Files.createDirectories(uploadPath);
            // 创建欲上传文件的输入流
            InputStream inputStream = file.getInputStream();
            // 上传文件
            Files.copy(inputStream, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ResourceStorageException("【资源存储异常】- 无法存储文件 " + originalFilename + " !", e);
        }

        // 存库，数据库标识
        return resourceRepository.save(new Resource(targetFilePath.toString().replace('\\', '/'), originalFilename, storageFilename, fileType, 0));
    }

    @Override
    public List<Resource> getResources() {
        return resourceRepository.findAll(ORDER_BY_CREATED_AT);
    }

    private String handleFileTypeField(String MIMEType) {
        switch (MIMEType.split("/")[0]) {
            case "text":
                return "文本";
            case "image":
                return "图像";
            case "audio":
                return "音频";
            case "video":
                return "视频";
            case "application":
                switch (MIMEType.split("/")[1]) {
                    case "pdf":
                        return "PDF 文档";
                    case "msword":
                    case "vnd.openxmlformats-officedocument.wordprocessingml.document":
                        return "Office Word 文档";
                    case "vnd.ms-excel":
                    case "vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                        return "Office Excel 文档";
                    case "vnd.ms-powerpoint":
                    case "vnd.openxmlformats-officedocument.presentationml.presentation":
                        return "Office PowerPoint 文档";
                }
                return "未知二进制";
            case "font":
                return "字体";
            case "model":
                return "3D";
            default:
                return "未知类型资源";
        }
    }
}
