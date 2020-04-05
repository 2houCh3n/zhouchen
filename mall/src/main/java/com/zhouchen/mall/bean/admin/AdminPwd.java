package com.zhouchen.mall.bean.admin;

/**
 * User：zhouchen
 * Time: 2020/3/30  14:34
 * Description:
 */
public class AdminPwd {
    //管理员昵称，唯一标识当前管理员
    private String adminToken;
    //旧密码
    private String oldPwd;
    //新密码
    private String newPwd;
    //核对新密码
    private String confirmPwd;

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdminToken(String adminToken) {
        this.adminToken = adminToken;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    @Override
    public String toString() {
        return "AdminPwd{" +
                "adminToken='" + adminToken + '\'' +
                ", oldPwd='" + oldPwd + '\'' +
                ", newPwd='" + newPwd + '\'' +
                ", confirmPwd='" + confirmPwd + '\'' +
                '}';
    }
}
