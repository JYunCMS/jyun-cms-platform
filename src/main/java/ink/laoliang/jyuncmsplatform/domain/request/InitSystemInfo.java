package ink.laoliang.jyuncmsplatform.domain.request;

public class InitSystemInfo {

    private String adminPassword;

    public InitSystemInfo() {
    }

    public InitSystemInfo(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
