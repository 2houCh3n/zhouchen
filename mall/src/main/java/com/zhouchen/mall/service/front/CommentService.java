package com.zhouchen.mall.service.front;

import com.zhouchen.mall.bean.comment.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getGoodComments(int goodId);

    void sendComment(Comment comment);
}
