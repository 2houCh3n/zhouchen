package com.zhouchen.mall.service.background.impl;

import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.bean.user.UserPwd;
import com.zhouchen.mall.dao.background.UserDao;
import com.zhouchen.mall.dao.background.impl.UserDaoImpl;
import com.zhouchen.mall.service.background.UserService;

import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/2  11:02
 * Description:
 */
public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public List<User> allUser() {
        return userDao.allUser();
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public List<User> searchUser(String word) {
        return userDao.searchUser(word);
    }

    @Override
    public User loginUser(User user) {
        return userDao.loginUser(user);
    }

    /**
     * 先去数据库去查找是否有该邮箱，有就返回1
     * 再去数据库查找是否有该昵称，有就返回2
     * 都没有的话，就将该用户添加到数据库，返回0
     * @param user
     * @return
     */
    @Override
    public int signupUser(User user) {
        User resUser = null;
        resUser = userDao.getUserByEmail(user.getEmail());
        if (resUser != null) {
            return 1;
        }
        resUser = userDao.getUserByNickname(user.getNickname());
        if (resUser != null) {
            return 2;
        }
        userDao.addUser(user);
        return 0;
    }

    @Override
    public User getUserInfo(String userName) {
        return userDao.getUserByNickname(userName);
    }

    /**
     * 用新的用户信息更新数据库中旧用户
     * 首先对昵称进行判断，是否被别人占用，是，就返回1
     * 如果正常更新，就返回0
     *
     * @param user
     * @return
     */
    @Override
    public int updateUserDate(User user) {
        User resUser = userDao.getUserByNickname(user.getNickname());
        if (resUser.getId().equals(user.getId())) {
            return 1;
        }
        userDao.updateUserData(user);
        return 0;
    }

    /**
     * 更新数据库用户密码
     * 1.如果旧密码错误，就返回1
     * 2.旧密码正确，就正常更新
     * @param userPwd
     * @return
     */
    @Override
    public int updateUserPwd(UserPwd userPwd) {
        User user = userDao.getUser(userPwd.getId());
        if (!user.getPwd().equals(userPwd.getOldPwd())) {
            return 1;
        }
        userDao.updateUserPwd(userPwd);
        return 0;
    }
}
