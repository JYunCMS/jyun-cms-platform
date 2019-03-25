package ink.laoliang.jyuncmsplatform.domain.options;

import java.io.Serializable;

public class SiteTitle implements Serializable {

    private String name;

    public SiteTitle() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
