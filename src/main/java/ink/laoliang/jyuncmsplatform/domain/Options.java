package ink.laoliang.jyuncmsplatform.domain;

import ink.laoliang.jyuncmsplatform.domain.options._OptionValue;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Component
public class Options implements Serializable {

    @Id
    @Column(columnDefinition = "char(20)")
    private String name;

    @Column(columnDefinition = "blob")
    private _OptionValue value;

    @Column(nullable = false)
    private Boolean bePublic;

    public Options() {
    }

    public Options(String name, _OptionValue value, Boolean bePublic) {
        this.name = name;
        this.value = value;
        this.bePublic = bePublic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public _OptionValue getValue() {
        return value;
    }

    public void setValue(_OptionValue value) {
        this.value = value;
    }

    public Boolean getBePublic() {
        return bePublic;
    }

    public void setBePublic(Boolean bePublic) {
        this.bePublic = bePublic;
    }
}
