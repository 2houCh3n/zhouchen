package com.zhouchen.mall.service.background.impl;

import com.zhouchen.mall.bean.admin.Admin;
import com.zhouchen.mall.bean.admin.AdminPwd;
import com.zhouchen.mall.dao.background.AdminDao;
import com.zhouchen.mall.dao.background.impl.AdminDaoImpl;
import com.zhouchen.mall.service.background.AdminService;

import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/1  15:07
 * Description:
 */
public class AdminServiceImpl implements AdminService {
    AdminDao adminDao = new AdminDaoImpl();

    /**
     * 去数据库查询指定的数据（邮箱 + 密码）
     * 1.先查询邮箱
     * 2.邮箱存在就继续查询密码
     * @param admin
     * @return
     * 若邮箱不存在，返回1
     * 若邮箱存在，密码不正确，返回2
     * 若都正确就返回0
     */
    @Override
    public int loginAdmin(Admin admin) {
        Admin resAdmin = adminDao.loginAdmin(admin);
        if (resAdmin == null) {
            return 1;
        } else if (!resAdmin.getPwd().equals(admin.getPwd())) {
            return 2;
        }
        admin.setNickname(resAdmin.getNickname());
        return 0;
    }

    /**
     * 从数据库获取所有的管理员信息，用List封装，并返回
     * @return
     */
    @Override
    public List<Admin> getAllAdmins() {
        return adminDao.getAllAdmins();
    }

    /**
     * 向数据库中添加新的管理员信息，在添加之前需要对admin进行判断：
     * 1.如果新添加的管理员的email已经存在，那么添加失败，返回1
     * 2.如果新添加的管理员的nickname已经存在，那么添加失败，返回2
     * 3.如果email和nickname都不存在，那么就成功添加，返回0
     *
     * @param admin
     * @return
     */
    @Override
    public int addAdmin(Admin admin) {
        Admin resAdmin = null;
        resAdmin = adminDao.getAdminByEmail(admin.getEmail());
        if (resAdmin != null) {
            return 1;
        }
        resAdmin = adminDao.getAdminByNick(admin.getNickname());
        if (resAdmin != null) {
            return 2;
        }
        adminDao.addAdmin(admin);
        return 0;
    }


    @Override
    public void deleteAdmin(int id) {
        adminDao.deleteAdmin(id);
    }

    @Override
    public Admin getAdminInfo(int id) {
        return adminDao.getAdminById(id);
    }

    @Override
    public int updateAdmin(Admin admin) {
        return adminDao.updateAdmin(admin);
    }

    @Override
    public List<Admin> getSearchAdmins(Admin admin) {
        return adminDao.getSearchAdmins(admin);
    }

    /**
     * 修改管理员密码
     * 在更新数据库之前，需要检查原密码是否正确，以及两次输入的新密码是否一致
     * @param adminPwd
     * @return
     * 若原密码错误，返回1
     * 若原密码正确，两次新密码不一致，返回2
     * 若正常修改返回0
     */
    @Override
    public int changePwd(AdminPwd adminPwd) {
        Admin resAdmin = adminDao.getAdminByNick(adminPwd.getAdminToken());
        if (!resAdmin.getPwd().equals(adminPwd.getOldPwd())) {
            return 1;
        }
        if (!adminPwd.getNewPwd().equals(adminPwd.getConfirmPwd())) {
            return 2;
        }
        resAdmin.setPwd(adminPwd.getNewPwd());
        adminDao.updateAdmin(resAdmin);
        return 0;
    }
}
