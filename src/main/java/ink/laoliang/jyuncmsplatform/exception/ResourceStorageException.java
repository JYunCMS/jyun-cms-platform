package ink.laoliang.jyuncmsplatform.exception;

public class ResourceStorageException extends RuntimeException {

    public ResourceStorageException(String message) {
        super(message);
    }

    public ResourceStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
