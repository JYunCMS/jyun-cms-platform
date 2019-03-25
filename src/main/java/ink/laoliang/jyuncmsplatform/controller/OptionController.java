package ink.laoliang.jyuncmsplatform.controller;

import ink.laoliang.jyuncmsplatform.domain.Options;
import ink.laoliang.jyuncmsplatform.domain.options.*;
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

    @PostMapping(value = "/site-title")
    public SiteTitle setSiteTitle(@RequestAttribute String USER_ROLE,
                                  @RequestBody SiteTitle siteTitle) {
        return optionService.setSiteTitle(USER_ROLE, siteTitle);
    }

    @PostMapping(value = "/copyright-info")
    public CopyrightInfo setCopyrightInfo(@RequestAttribute String USER_ROLE,
                                          @RequestBody CopyrightInfo copyrightInfo) {
        return optionService.setCopyrightInfo(USER_ROLE, copyrightInfo);
    }

    @PostMapping(value = "/website-filing-info")
    public WebsiteFilingInfo setWebsiteFilingInfo(@RequestAttribute String USER_ROLE,
                                                  @RequestBody WebsiteFilingInfo websiteFilingInfo) {
        return optionService.setWebsiteFilingInfo(USER_ROLE, websiteFilingInfo);
    }

    @PostMapping(value = "/home-carousel-images")
    public List<HomeCarouselImages> setHomeCarouselImages(@RequestAttribute String USER_ROLE,
                                                          @RequestBody List<HomeCarouselImages> homeCarouselImages) {
        return optionService.setHomeCarouselImages(USER_ROLE, homeCarouselImages);
    }

    @PostMapping(value = "/friendly-links")
    public List<FriendlyLinks> setFriendlyLinks(@RequestAttribute String USER_ROLE,
                                                @RequestBody List<FriendlyLinks> friendlyLinks) {
        return optionService.setFriendlyLinks(USER_ROLE, friendlyLinks);
    }
}
