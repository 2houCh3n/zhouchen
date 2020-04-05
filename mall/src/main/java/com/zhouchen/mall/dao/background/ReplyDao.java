package com.zhouchen.mall.dao.background;

import com.zhouchen.mall.bean.reply.Question;

import java.util.List;

public interface ReplyDao {
    List<Question> getReplyMsg(int state);

    Question getReplyInfo(Integer id);

    void updateReply(Question noReplyQue);

    List<Question> getGoodMsgs(int goodId);

    void askGoodMsg(Question question);
}
