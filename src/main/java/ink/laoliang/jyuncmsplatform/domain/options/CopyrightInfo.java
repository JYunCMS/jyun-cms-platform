package ink.laoliang.jyuncmsplatform.domain.options;

import java.io.Serializable;

public class CopyrightInfo implements Serializable {

    private String name;

    public CopyrightInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
