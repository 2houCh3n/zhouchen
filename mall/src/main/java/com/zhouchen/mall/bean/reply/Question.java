package com.zhouchen.mall.bean.reply;

import com.zhouchen.mall.bean.Good.Good;
import com.zhouchen.mall.bean.user.User;

import java.util.Date;

/**
 * User：zhouchen
 * Time: 2020/4/2  20:14
 * Description:
 */
public class Question {
    //问答id
    private Integer id;
    //问答用户id
    private Integer userId;
    //问答商品id
    private Integer goodId;
    //问答内容
    private String content;
    //回复内容
    private String replyContent;
    //问答创建时间
    private String createTime;
    //问答订单状态id
    private Integer state;
    //商品信息
    private Good good;
    //用户信息
    private User user;
    //问答用户名
    private String asker;
    //回复的信息
    private Reply reply;
    //回复内容
    private String replyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAsker() {
        return asker;
    }

    public void setAsker(String asker) {
        this.asker = asker;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodId=" + goodId +
                ", content='" + content + '\'' +
                ", replyContent='" + replyContent + '\'' +
                ", createTime='" + createTime + '\'' +
                ", state=" + state +
                ", good=" + good +
                ", user=" + user +
                ", asker='" + asker + '\'' +
                ", reply=" + reply +
                ", replyTime='" + replyTime + '\'' +
                '}';
    }
}
