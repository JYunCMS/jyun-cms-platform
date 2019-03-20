package ink.laoliang.jyuncmsplatform.domain.request;

public class InitJYunCmsInfo {

    private String adminPassword;

    public InitJYunCmsInfo() {
    }

    public InitJYunCmsInfo(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
