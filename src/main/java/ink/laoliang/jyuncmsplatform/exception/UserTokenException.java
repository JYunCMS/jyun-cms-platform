package ink.laoliang.jyuncmsplatform.exception;

public class UserTokenException extends RuntimeException {

    public UserTokenException(String message) {
        super(message);
    }

    public UserTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
