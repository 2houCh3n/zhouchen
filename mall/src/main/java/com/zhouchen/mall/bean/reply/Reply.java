package com.zhouchen.mall.bean.reply;

import java.util.Date;

/**
 * User：zhouchen
 * Time: 2020/4/3  16:45
 * Description:
 */
public class Reply {
    //回复内容
    private String content;
    //回复时间
    private String replyTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "content='" + content + '\'' +
                ", replyTime='" + replyTime + '\'' +
                '}';
    }
}
