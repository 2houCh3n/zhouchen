package com.zhouchen.mall.service.background.impl;

import com.zhouchen.mall.bean.order.Order;
import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.bean.user.UserPwd;
import com.zhouchen.mall.dao.background.OrderDao;
import com.zhouchen.mall.dao.background.ReplyDao;
import com.zhouchen.mall.dao.background.UserDao;
import com.zhouchen.mall.dao.background.impl.OrderDaoImpl;
import com.zhouchen.mall.dao.background.impl.ReplyDaoImpl;
import com.zhouchen.mall.dao.background.impl.UserDaoImpl;
import com.zhouchen.mall.dao.front.CommentDao;
import com.zhouchen.mall.dao.front.impl.CommentDaoImpl;
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

    /**
     * 删除指定用户
     * 1.首先对用户进行判断，如果该用户还有未完成的订单，是无法删除的，然后返回1。
     *                          只有所有订单都完成，才能删除
     * 2.然后从数据库删除该用户
     * 2.再从数据库删除该用户对应的订单和问答以及评论，返回0
     * @param userId
     */
    @Override
    public int deleteUser(int userId) {
        OrderDao orderDao = new OrderDaoImpl();
        List<Order> orders = orderDao.getOrdersByUser(userId);
        for (Order order : orders) {
            if (order.getStateId() != 3 && order.getDeleteFlag() == 0) {
                //还有未完成的订单
                return 1;
            }
        }
        //所有订单都已完成，可以删除该用户
        //删除用户
        userDao.deleteUser(userId);
        //删除该用户对应的订单
        orderDao.deleteOrders(userId);
        //删除该用户对应的评论
        CommentDao commentDao = new CommentDaoImpl();
        commentDao.deleteComments(userId);
        //删除该用户对应的问答
        ReplyDao replyDao = new ReplyDaoImpl();
        replyDao.deleteReplys(userId);
        return 0;
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
        if (resUser != null) {
            if (!resUser.getId().equals(user.getId())) {
                //昵称被占用
                return 1;
            } else {
                userDao.updateUserData(user);
            }
        } else {
            userDao.updateUserData(user);
        }
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
