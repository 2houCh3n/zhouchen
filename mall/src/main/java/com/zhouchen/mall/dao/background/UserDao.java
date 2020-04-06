package com.zhouchen.mall.dao.background;

import com.zhouchen.mall.bean.order.Order;
import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.bean.user.UserPwd;

import java.util.List;

public interface UserDao {
    List<User> allUser();

    void deleteUser(int id);

    List<User> searchUser(String word);

    User getUser(Integer userId);

    User loginUser(User user);

    User getUserByEmail(String email);

    User getUserByNickname(String nickname);

    void addUser(User user);

    void updateUserData(User user);

    void updateUserPwd(UserPwd userPwd);
}
