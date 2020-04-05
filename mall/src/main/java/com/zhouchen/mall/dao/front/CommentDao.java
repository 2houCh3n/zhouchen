package com.zhouchen.mall.dao.front;

import com.zhouchen.mall.bean.comment.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> getGoodComments(int goodId);

    void sendComment(Comment comment);
}
