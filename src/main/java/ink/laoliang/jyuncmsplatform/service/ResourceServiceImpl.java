package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Resource;
import ink.laoliang.jyuncmsplatform.domain.response.ResourceFilterConditions;
import ink.laoliang.jyuncmsplatform.exception.ResourceStorageException;
import ink.laoliang.jyuncmsplatform.exception.UserRolePermissionException;
import ink.laoliang.jyuncmsplatform.repository.ResourceRepository;
import ink.laoliang.jyuncmsplatform.util.QueryDateRange;
import ink.laoliang.jyuncmsplatform.config.UserRoleFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final Sort ORDER_BY_CREATED_AT = new Sort(Sort.Direction.DESC, "createdAt");

    @Value("${custom.upload-dir}")
    private String UPLOAD_DIR;

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Resource upload(MultipartFile file) {
        // 原始文件名
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

        if (file.isEmpty()) {
            throw new ResourceStorageException("【资源存储异常】- 无法存储空文件！");
        }
        if (originalFilename.contains("..")) {
            throw new ResourceStorageException("【资源存储异常】- 无法存储当前目录外的相对路径的文件！");
        }

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
        Path location = uploadPath.resolve(storageFilename);
        // 文件类型
        String fileType = handleFileTypeField(file.getContentType());
        // 文件大小
        String fileSize = handleFileSizeField(file.getSize());

        try {
            // 创建文件夹（如果已存在则直接返回）
            Files.createDirectories(uploadPath);
            // 创建欲上传文件的输入流
            InputStream inputStream = file.getInputStream();
            // 上传文件
            Files.copy(inputStream, location, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ResourceStorageException("【资源存储异常】- 无法存储文件！", e);
        }

        // 存库，数据库标识
        return resourceRepository.save(new Resource(location.toString().replace('\\', '/'), originalFilename, storageFilename, fileType, fileSize, false));
    }

    @Override
    public List<Resource> getResources() {
        return resourceRepository.findAll(ORDER_BY_CREATED_AT);
    }

    @Override
    public List<Resource> deleteResource(String USER_ROLE, String location) {
        // 验证用户角色权限
        if (UserRoleFields.getUserRoleLevel(USER_ROLE) <= 1) {
            throw new UserRolePermissionException("【用户角色权限异常】- 当前用户角色等级没有删除资源文件的权限！");
        }

        // 删库（资源在库中的对应标记行）
        resourceRepository.deleteById(location);

        // 删除磁盘上对应文件
        try {
            Files.delete(Paths.get(location));
        } catch (NoSuchFileException e) {
            // 文件删除失败，磁盘上已经没有该文件！（已知的异常，上面已经删了库，这里直接返回即可）
            return resourceRepository.findAll(ORDER_BY_CREATED_AT);
        } catch (IOException e) {
            throw new ResourceStorageException("【资源存储异常】- 文件删除失败！", e);
        }

        return resourceRepository.findAll(ORDER_BY_CREATED_AT);
    }

    @Override
    public ResourceFilterConditions getFilterConditions() {
        Path uploadDir = Paths.get(UPLOAD_DIR);
        List<String> dateList = new ArrayList<>();
        try {
            // 创建文件夹，防止资源文件夹不存在（如果已存在则直接返回）
            Files.createDirectories(uploadDir);
            Files.walkFileTree(uploadDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (dir != uploadDir) {
                        dateList.add(dir.getFileName().toString());
                    }
                    return super.preVisitDirectory(dir, attrs);
                }
            });
        } catch (IOException e) {
            throw new ResourceStorageException("【资源存储异常】- 资源文件夹 " + uploadDir + " 下日期分类列表获取失败！", e);
        }

        return new ResourceFilterConditions(dateList, resourceRepository.getFileTypes());
    }

    @Override
    public List<Resource> getByConditions(String date, String type) {
        Map<String, Date> dateMap = QueryDateRange.handle(date);

        if (type == null || type.equals("") || type.equals("null")) {
            type = "%";
        }

        return resourceRepository.findAllByConditions(dateMap.get("startDate"), dateMap.get("endDate"), type);
    }

    private String handleFileTypeField(String MIMEType) {
        if (MIMEType == null) {
            return "未知类型资源";
        }

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

    private String handleFileSizeField(long byteSize) {
        long KBSize = byteSize / 1024;
        if (KBSize < 1) {
            return byteSize + " Byte";
        }

        long MBSize = byteSize / (1024 * 1024);
        if (MBSize < 1) {
            String pointNumber = "." + byteSize % 1024;
            if (pointNumber.length() > 3) {
                pointNumber = pointNumber.substring(0, 3);
            }
            return KBSize + pointNumber + " KB";
        }

        long GBSize = byteSize / (1024 * 1024 * 1024);
        if (GBSize < 1) {
            String pointNumber = "." + byteSize % (1024 * 1024);
            if (pointNumber.length() > 3) {
                pointNumber = pointNumber.substring(0, 3);
            }
            return MBSize + pointNumber + " MB";
        }

        String pointNumber = "." + byteSize % (1024 * 1024 * 1024);
        if (pointNumber.length() > 3) {
            pointNumber = pointNumber.substring(0, 3);
        }
        return GBSize + pointNumber + " GB";
    }
}
