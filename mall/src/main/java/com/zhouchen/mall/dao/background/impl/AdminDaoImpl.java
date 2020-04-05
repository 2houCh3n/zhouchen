package com.zhouchen.mall.dao.background.impl;

import com.alibaba.druid.util.StringUtils;
import com.zhouchen.mall.bean.admin.Admin;
import com.zhouchen.mall.dao.background.AdminDao;
import com.zhouchen.mall.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/1  15:17
 * Description:
 */
public class AdminDaoImpl implements AdminDao {
    /**
     * 去admins表中查询指定email的记录
     * @param admin
     * @return
     * 将查询的结果封装成Admin对象返回
     */
    public Admin loginAdmin(Admin admin) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Admin query = null;
        try {
            query = runner.query(
                    "select * from admins where email=?",
                    new BeanHandler<Admin>(Admin.class),
                    admin.getEmail()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * 从admins表中获取所有的信息。封装在List中，然后返回
     * @return
     */
    @Override
    public List<Admin> getAllAdmins() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Admin> admins = null;
        try {
            admins = runner.query(
                    "select * from admins",
                    new BeanListHandler<>(Admin.class)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    /**
     * 从admins表中查找指定nickname的管理员信息
     * @param nickname
     * @return
     */
    @Override
    public Admin getAdminByNick(String nickname) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Admin admin = null;
        try {
            admin = runner.query(
                    "select * from admins where nickname=?",
                    new BeanHandler<>(Admin.class),
                    nickname
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    /**
     * 向admins表中插入指定的管理员信息
     * @param admin
     */
    @Override
    public void addAdmin(Admin admin) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "insert into admins (email,nickname,pwd) values (?,?,?)",
                    admin.getEmail(),
                    admin.getNickname(),
                    admin.getPwd()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从admins表中删除指定id的管理员
     * @param id
     */
    @Override
    public void deleteAdmin(int id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "delete from admins where id=?",
                    id
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从admins表中查找指定id的管理员信息
     * @param id
     * @return
     */
    @Override
    public Admin getAdminById(int id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Admin admin = null;
        try {
            admin = runner.query(
                    "select * from admins where id=?",
                    new BeanHandler<>(Admin.class),
                    id
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    /**
     * 向数据库中更新管理员信息，在添加之前需要对admin进行判断：
     * 1.如果新添加的管理员的email已经存在，那么添加失败，返回1
     * 2.如果新添加的管理员的nickname已经存在，那么添加失败，返回2
     * 3.如果email和nickname都不存在，那么就成功更新，返回0
     * @param admin
     * @return
     */
    @Override
    public int updateAdmin(Admin admin) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            Admin queryEmail = runner.query(
                    "select * from admins where email=? and id!=?",
                    new BeanHandler<>(Admin.class),
                    admin.getEmail(),
                    admin.getId());
            Admin queryNickname = runner.query(
                    "select * from admins where nickname=? and id!=?",
                    new BeanHandler<>(Admin.class),
                    admin.getNickname(),
                    admin.getId());
            if (queryEmail != null) {
                return 1;
            }
            if (queryNickname != null) {
                return 2;
            }
            runner.update(
                    "update admins set email = ?, nickname = ?, pwd = ? where id = ?",
                    admin.getEmail(),
                    admin.getNickname(),
                    admin.getPwd(),
                    admin.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据输入的信息在数据库中查找指定的数据，并将数据以List形式返回
     * 这里采用了模糊查询
     * 考虑到查询条件没有填写的情况，这里涉及到了sql语句的拼接
     * @param admin
     * @return
     */
    @Override
    public List<Admin> getSearchAdmins(Admin admin) {
        String sql = "select * from admins where 1=1";
        List<Object> params = new ArrayList<>();
        if (!StringUtils.isEmpty(admin.getEmail())) {
            sql = sql + " and email like ?";
            params.add("%" + admin.getEmail() + "%");
        }
        if (!StringUtils.isEmpty(admin.getNickname())) {
            sql = sql + " and nickname like ?";
            params.add("%" + admin.getNickname() + "%");
        }

        //根据拼接的sql语句查找指定的数据
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Admin> admins = null;
        try {
            admins = runner.query(sql, new BeanListHandler<Admin>(Admin.class),params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    /**
     * 从admins表中查找指定email的管理员信息
     * @param email
     * @return
     */
    @Override
    public Admin getAdminByEmail(String email) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Admin admin = null;
        try {
            admin = runner.query(
                    "select * from admins where email=?",
                    new BeanHandler<>(Admin.class),
                    email
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
}
