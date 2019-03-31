package ink.laoliang.jyuncmsplatform.domain.options;

import java.io.Serializable;

public class _OptionValue<T> implements Serializable {

    private T content;

    public _OptionValue() {
    }

    public _OptionValue(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
