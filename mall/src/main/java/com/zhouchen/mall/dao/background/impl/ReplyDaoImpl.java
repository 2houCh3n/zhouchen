package com.zhouchen.mall.dao.background.impl;

import com.zhouchen.mall.bean.reply.Question;
import com.zhouchen.mall.dao.background.ReplyDao;
import com.zhouchen.mall.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/2  11:07
 * Description:
 */
public class ReplyDaoImpl implements ReplyDao {
    /**
     * 从questions表中查找指定状态（回复或为未回复）的问题，以list形式返回
     * @param state
     * @return
     */
    @Override
    public List<Question> getReplyMsg(int state) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Question> questions = null;
        try {
            questions = runner.query(
                    "select * from questions where state=? order by createTime desc",
                    new BeanListHandler<>(Question.class),
                    state
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    /**
     * 根据指定id值，从questions表中获取相应的详细信息
     * @param id
     * @return
     * 返回相应的Question对象
     */
    @Override
    public Question getReplyInfo(Integer id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Question query = null;
        try {
            query = runner.query(
                    "select * from questions where id=?",
                    new BeanHandler<>(Question.class),
                    id
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * 更新留言
     * @param noReplyQue
     */
    @Override
    public void updateReply(Question noReplyQue) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "update questions set state=1, replyContent=?, replyTime=? where id=?",
                    noReplyQue.getReplyContent(),
                    noReplyQue.getReplyTime(),
                    noReplyQue.getId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从questions表中获取指定商品的问答，以list形式返回
     * @param goodId
     * @return
     */
    @Override
    public List<Question> getGoodMsgs(int goodId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Question> questions = null;
        try {
            questions = runner.query(
                    "select * from questions where goodId=? order by createTime desc",
                    new BeanListHandler<>(Question.class),
                    goodId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    /**
     * 将新的问题添加到questions表中
     * @param question
     */
    @Override
    public void askGoodMsg(Question question) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "insert into questions (userId,goodId,content,state,createtime) values (?,?,?,0,?)",
                    question.getUserId(),
                    question.getGoodId(),
                    question.getContent(),
                    question.getCreateTime().toString()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
