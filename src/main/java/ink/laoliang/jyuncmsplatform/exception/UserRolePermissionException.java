package ink.laoliang.jyuncmsplatform.exception;

public class UserRolePermissionException extends RuntimeException {

    public UserRolePermissionException(String message) {
        super(message);
    }

    public UserRolePermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
