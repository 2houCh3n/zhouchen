package com.zhouchen.mall.dao.background.impl;

import com.zhouchen.mall.bean.order.Order;
import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.bean.user.UserPwd;
import com.zhouchen.mall.dao.background.UserDao;
import com.zhouchen.mall.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/2  11:03
 * Description:
 */
public class UserDaoImpl implements UserDao {
    /**
     * 从users表中获取所有的用户信息，封装在List中返回
     * @return
     */
    @Override
    public List<User> allUser() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<User> users = null;
        try {
            users = runner.query(
                    "select * from users",
                    new BeanListHandler<>(User.class)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 根据指定id，在users表中删除指定用户信息
     * @param id
     */
    @Override
    public void deleteUser(int id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "delete from users where id=?",
                    id
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传过来的字符串，在users表中模糊查询相应的用户信息，封装在list中返回
     * @param word
     * @return
     */
    @Override
    public List<User> searchUser(String word) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<User> users = null;
        try {
            users = runner.query(
                    "select * from users where nickname like ?",
                    new BeanListHandler<>(User.class),
                    "%" + word + "%"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 从users表中获取指定的user
     * @param userId
     * @return
     */
    @Override
    public User getUser(Integer userId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        User user = null;
        try {
            user = runner.query(
                    "select * from users where id=?",
                    new BeanHandler<>(User.class),
                    userId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 根据参数去users表中查找与之匹配的用户，将查到的结果返回
     * @param user
     * @return
     */
    @Override
    public User loginUser(User user) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        User query = null;
        try {
            query = runner.query(
                    "select * from users where email=? and pwd=?",
                    new BeanHandler<>(User.class),
                    user.getEmail(),
                    user.getPwd()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * 从users表中获取指定email的用户信息
     * @param email
     * @return
     */
    @Override
    public User getUserByEmail(String email) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        User user = null;
        try {
            user = runner.query(
                    "select * from users where email=?",
                    new BeanHandler<>(User.class),
                    email
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 从users表中获取指定昵称的用户信息
     * @param nickname
     * @return
     */
    @Override
    public User getUserByNickname(String nickname) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        User user = null;
        try {
            user = runner.query(
                    "select * from users where nickname=?",
                    new BeanHandler<>(User.class),
                    nickname
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 将指定的用户添加到users表中
     * @param user
     */
    @Override
    public void addUser(User user) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "insert into users (email,nickname,pwd,recipient,address,phone,img) values(?,?,?,?,?,?,?)",
                    user.getEmail(),
                    user.getNickname(),
                    user.getPwd(),
                    user.getRecipient(),
                    user.getAddress(),
                    user.getPhone(),
                    user.getImg()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新users表中的信息
     * @param user
     */
    @Override
    public void updateUserData(User user) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "update users set nickname=?,recipient=?,address=?,phone=? where id=?",
                    user.getNickname(),
                    user.getRecipient(),
                    user.getAddress(),
                    user.getPhone(),
                    user.getId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新用户密码
     * @param userPwd
     */
    @Override
    public void updateUserPwd(UserPwd userPwd) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "update users set pwd=? where id=?",
                    userPwd.getNewPwd(),
                    userPwd.getId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
