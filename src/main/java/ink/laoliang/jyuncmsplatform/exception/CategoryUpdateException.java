package ink.laoliang.jyuncmsplatform.exception;

public class CategoryUpdateException extends RuntimeException {

    public CategoryUpdateException(String message) {
        super(message);
    }

    public CategoryUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
