package com.zhouchen.mall.service.front.impl;

import com.zhouchen.mall.bean.Good.Spec;
import com.zhouchen.mall.bean.comment.Comment;
import com.zhouchen.mall.bean.order.Order;
import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.dao.background.GoodDao;
import com.zhouchen.mall.dao.background.OrderDao;
import com.zhouchen.mall.dao.background.UserDao;
import com.zhouchen.mall.dao.background.impl.GoodDaoImpl;
import com.zhouchen.mall.dao.background.impl.OrderDaoImpl;
import com.zhouchen.mall.dao.background.impl.UserDaoImpl;
import com.zhouchen.mall.dao.front.CommentDao;
import com.zhouchen.mall.dao.front.impl.CommentDaoImpl;
import com.zhouchen.mall.service.front.CommentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/3  16:59
 * Description:
 */
public class CommentServiceImpl implements CommentService {
    CommentDao commentDao = new CommentDaoImpl();

    /**
     * 先从数据库获取指定商品的所有评论
     * 再根据每一条评论来获取该评论的用户
     * @param goodId
     * @return
     */
    @Override
    public List<Comment> getGoodComments(int goodId) {
        List<Comment> comments = commentDao.getGoodComments(goodId);
        for (Comment comment : comments) {
            UserDao userDao = new UserDaoImpl();
            comment.setUser(userDao.getUser(comment.getUserId()));
        }
        return comments;
    }

    /**
     * 对指定商品添加新的评论
     * 1.首先根据用户名，获取用户详细信息，将之添加到新评论中
     * 2.再根据规格id获取详细的规格信息，将之添加到新评论中
     * 3.将新评论添加到数据库,同时置该订单状态为已评论
     * @param comment
     */
    @Override
    public void sendComment(Comment comment) {
        UserDao userDao = new UserDaoImpl();
        GoodDao goodDao = new GoodDaoImpl();
        OrderDao orderDao = new OrderDaoImpl();
        //获取用户信息
        User user = userDao.getUserByNickname(comment.getName());
        //获取规格信息
        Spec spec = goodDao.getSpec(comment.getSpecId());
        comment.setSpecName(spec.getSpecName());
        comment.setUserId(user.getId());
        commentDao.sendComment(comment);
        //更新该订单状态
        orderDao.setHasComment(comment.getOrderId());
    }
}
