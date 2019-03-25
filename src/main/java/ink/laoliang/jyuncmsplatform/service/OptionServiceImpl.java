package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.config.OptionsFields;
import ink.laoliang.jyuncmsplatform.config.UserRoleFields;
import ink.laoliang.jyuncmsplatform.domain.Options;
import ink.laoliang.jyuncmsplatform.domain.Resource;
import ink.laoliang.jyuncmsplatform.domain.options.HomeCarouselImages;
import ink.laoliang.jyuncmsplatform.domain.options._OptionValue;
import ink.laoliang.jyuncmsplatform.exception.IllegalParameterException;
import ink.laoliang.jyuncmsplatform.exception.UserRolePermissionException;
import ink.laoliang.jyuncmsplatform.repository.OptionRepository;
import ink.laoliang.jyuncmsplatform.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;

    private final ResourceRepository resourceRepository;

    @Autowired
    public OptionServiceImpl(OptionRepository optionRepository, ResourceRepository resourceRepository) {
        this.optionRepository = optionRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public List<Options> getOptions() {
        return optionRepository.findAll();
    }

    @Override
    public List<HomeCarouselImages> setHomeCarouselImages(String USER_ROLE, List<HomeCarouselImages> homeCarouselImages) {
        // 验证用户角色权限
        if (UserRoleFields.getUserRoleLevel(USER_ROLE) < 2) {
            throw new UserRolePermissionException("【用户角色权限异常】- 当前用户不能设置首页轮播图！");
        }

        // 标注资源为引用状态
        for (HomeCarouselImages homeCarouselImage : homeCarouselImages) {
            Resource oldImageResource = resourceRepository.findById(homeCarouselImage.getImageLocation()).orElse(null);
            if (oldImageResource == null) {
                throw new IllegalParameterException("【非法参数异常】- 访问服务器不存在的资源！");
            }
            oldImageResource.setBeReference(true);
            resourceRepository.save(oldImageResource);
        }

        // 不再使用的轮播图资源更新引用状态为 false
        Options carouselImageOption = optionRepository.findById(OptionsFields.HOME_CAROUSEL_IMAGES).orElse(null);
        if (carouselImageOption != null) {
            for (HomeCarouselImages tempCarouselImage : (List<HomeCarouselImages>) carouselImageOption.getValue().getContent()) {
                List<String> stringHomeCarouselImages = new ArrayList<>();
                for (HomeCarouselImages homeCarouselImage : homeCarouselImages) {
                    stringHomeCarouselImages.add(homeCarouselImage.getImageLocation());
                }
                if (!stringHomeCarouselImages.contains(tempCarouselImage.getImageLocation())) {
                    Resource oldImageResource = resourceRepository.findById(tempCarouselImage.getImageLocation()).orElse(null);
                    if (oldImageResource == null) {
                        throw new IllegalParameterException("【非法参数异常】- 访问服务器不存在的资源！");
                    }
                    oldImageResource.setBeReference(false);
                    resourceRepository.save(oldImageResource);
                }
            }
        }

        Options option = optionRepository.save(new Options(OptionsFields.HOME_CAROUSEL_IMAGES, new _OptionValue<>(homeCarouselImages)));
        return (List<HomeCarouselImages>) option.getValue().getContent();
    }
}
