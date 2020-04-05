package com.zhouchen.mall.service.background.impl;

import com.zhouchen.mall.bean.Good.Good;
import com.zhouchen.mall.bean.reply.Question;
import com.zhouchen.mall.bean.reply.Reply;
import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.dao.background.GoodDao;
import com.zhouchen.mall.dao.background.ReplyDao;
import com.zhouchen.mall.dao.background.UserDao;
import com.zhouchen.mall.dao.background.impl.GoodDaoImpl;
import com.zhouchen.mall.dao.background.impl.ReplyDaoImpl;
import com.zhouchen.mall.dao.background.impl.UserDaoImpl;
import com.zhouchen.mall.service.background.ReplyService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/2  11:06
 * Description:
 */
public class ReplyServiceImpl implements ReplyService {
    ReplyDao replyDao = new ReplyDaoImpl();
    /**
     * 先从数据库中获取所有的已回复消息
     * 然后分别获取每条消息对应的商品信息和用户信息
     * 以list形式返回
     * @param state
     * @return
     */
    @Override
    public List<Question> getReplyMsg(int state) {
        List<Question> replyMsgs = replyDao.getReplyMsg(state);

        //从数据库中分别获取goods信息，和user信息
        GoodDao goodDao = new GoodDaoImpl();
        UserDao userDao = new UserDaoImpl();
        for (Question question : replyMsgs) {
            Good good = new Good();
            good.setName(goodDao.getGood(question.getGoodId()).getName());
            User user = new User();
            user.setNickname(userDao.getUser(question.getUserId()).getNickname());
            question.setGood(good);
            question.setUser(user);
        }
        return replyMsgs;
    }

    /**
     * 将刚刚回复的留言的标志位置为已回复
     * 在这之前需要去数据库通过id从questions中将完整的数据取出来
     * 然后再在上面的完整数据进行修改
     * 然后再写入数据库
     * @param question
     */
    @Override
    public void reply(Question question) {
        //从数据库中获取刚刚回复的留言的原始数据
        Question noReplyQue = replyDao.getReplyInfo(question.getId());
        //将回复的信息添加到原始数据中，同时置回复标志位
        noReplyQue.setReplyContent(question.getReplyContent());
        noReplyQue.setReplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //再将重置后的留言写入数据库
        replyDao.updateReply(noReplyQue);
    }

    @Override
    public List<Question> getGoodMsgs(int goodId) {
        List<Question> questions = replyDao.getGoodMsgs(goodId);
        for (Question question : questions) {
            Reply reply = new Reply();
            reply.setContent(question.getReplyContent());
            reply.setReplyTime(question.getReplyTime());
            UserDao userDao = new UserDaoImpl();
            question.setAsker(userDao.getUser(question.getUserId()).getNickname());
            question.setReply(reply);
        }
        return questions;
    }

    /**
     * 先根据用户昵称从数据库中获取对应的用户详细信息
     * 再将详细的信息添加到数据库
     * @param question
     */
    @Override
    public void askGoodMsg(Question question) {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.getUserByNickname(question.getAsker());
        question.setUserId(user.getId());
        question.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        replyDao.askGoodMsg(question);
    }
}
