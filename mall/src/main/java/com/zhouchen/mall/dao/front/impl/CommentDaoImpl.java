package com.zhouchen.mall.dao.front.impl;

import com.zhouchen.mall.bean.comment.Comment;
import com.zhouchen.mall.dao.front.CommentDao;
import com.zhouchen.mall.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/3  17:00
 * Description:
 */
public class CommentDaoImpl implements CommentDao {
    /**
     * 从comments表中获取指定商品的所有评论，以list形式返回
     * @param goodId
     * @return
     */
    @Override
    public List<Comment> getGoodComments(int goodId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Comment> comments = null;
        try {
            comments = runner.query(
                    "select * from comments where goodId=? order by time desc",
                    new BeanListHandler<>(Comment.class),
                    goodId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    /**
     * 向comments表中添加新的评论
     * @param comment
     */
    @Override
    public void sendComment(Comment comment) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "insert into comments (score,specName,comment,time,userId,goodId) values(?,?,?,NOW(),?,?)",
                    comment.getScore(),
                    comment.getSpecName(),
                    comment.getComment(),
                    comment.getUserId(),
                    comment.getGoodId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
