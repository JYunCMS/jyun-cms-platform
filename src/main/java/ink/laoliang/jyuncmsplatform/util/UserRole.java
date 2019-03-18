package ink.laoliang.jyuncmsplatform.util;

public class UserRole {

    public static final String SUPER_ADMIN = "超级管理员";

    public static final String ORDINARY_ADMIN = "普通管理员";

    public static final String COPYWRITER = "撰稿人";

    public static int getUserRoleLevel(String roleName) {
        switch (roleName) {
            case SUPER_ADMIN:
                return 3;
            case ORDINARY_ADMIN:
                return 2;
            case COPYWRITER:
                return 1;
            default:
                return 0;
        }
    }
}
