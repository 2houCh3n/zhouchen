package com.zhouchen.mall.service.background;

import com.zhouchen.mall.bean.reply.Question;

import java.util.List;

public interface ReplyService {

    List<Question> getReplyMsg(int state);

    void reply(Question question);

    List<Question> getGoodMsgs(int goodId);

    void askGoodMsg(Question question);
}
