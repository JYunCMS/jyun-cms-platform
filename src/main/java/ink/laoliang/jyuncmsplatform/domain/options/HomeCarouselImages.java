package ink.laoliang.jyuncmsplatform.domain.options;

import java.io.Serializable;

public class HomeCarouselImages implements Serializable {

    private String imageLocation;

    public HomeCarouselImages() {
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }
}
