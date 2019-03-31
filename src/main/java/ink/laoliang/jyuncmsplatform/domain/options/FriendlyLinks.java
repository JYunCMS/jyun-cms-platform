package ink.laoliang.jyuncmsplatform.domain.options;

import java.io.Serializable;

public class FriendlyLinks implements Serializable {

    private String title;

    private String link;

    public FriendlyLinks() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
