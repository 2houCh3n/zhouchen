package com.zhouchen.mall.service.background;

import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.bean.user.UserPwd;

import java.util.List;

public interface UserService {
    List<User> allUser();

    void deleteUser(int id);

    List<User> searchUser(String word);

    User loginUser(User user);

    int signupUser(User user);

    User getUserInfo(String userName);

    int updateUserDate(User user);

    int updateUserPwd(UserPwd userPwd);
}
