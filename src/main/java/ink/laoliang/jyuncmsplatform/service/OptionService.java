package ink.laoliang.jyuncmsplatform.service;

import ink.laoliang.jyuncmsplatform.domain.Options;
import ink.laoliang.jyuncmsplatform.domain.options.HomeCarouselImages;

import java.util.List;

public interface OptionService {

    List<Options> getOptions();

    List<HomeCarouselImages> setHomeCarouselImages(String USER_ROLE, List<HomeCarouselImages> homeCarouselImages);
}
