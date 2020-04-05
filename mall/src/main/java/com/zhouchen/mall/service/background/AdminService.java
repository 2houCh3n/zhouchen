package com.zhouchen.mall.service.background;

import com.zhouchen.mall.bean.admin.Admin;
import com.zhouchen.mall.bean.admin.AdminPwd;

import java.util.List;

public interface AdminService {
    int loginAdmin(Admin admin);

    List<Admin> getAllAdmins();

    int addAdmin(Admin admin);

    void deleteAdmin(int id);

    Admin getAdminInfo(int id);

    int updateAdmin(Admin admin);

    List<Admin> getSearchAdmins(Admin admin);

    int changePwd(AdminPwd adminPwd);
}
