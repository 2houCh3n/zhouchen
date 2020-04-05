package com.zhouchen.mall.dao.background;

import com.zhouchen.mall.bean.admin.Admin;

import java.util.List;

public interface AdminDao {
    Admin loginAdmin(Admin admin);

    List<Admin> getAllAdmins();

    Admin getAdminByEmail(String email);

    Admin getAdminByNick(String nickname);

    void addAdmin(Admin admin);

    void deleteAdmin(int id);

    Admin getAdminById(int id);

    int updateAdmin(Admin admin);

    List<Admin> getSearchAdmins(Admin admin);
}
