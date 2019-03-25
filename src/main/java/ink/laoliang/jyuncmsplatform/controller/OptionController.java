package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.Options;
import ink.laoliang.jyuncmsplatform.domain.options.HomeCarouselImages;
import ink.laoliang.jyuncmsplatform.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/options")
public class OptionController {

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public List<Options> getOptions() {
        return optionService.getOptions();
    }

    @PostMapping(value = "/home-carousel-images")
    public List<HomeCarouselImages> setHomeCarouselImages(@RequestAttribute String USER_ROLE,
                                                          @RequestBody List<HomeCarouselImages> homeCarouselImages) {
        return optionService.setHomeCarouselImages(USER_ROLE, homeCarouselImages);
    }
}
