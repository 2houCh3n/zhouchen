package com.zhouchen.mall.bean.comment;

import com.zhouchen.mall.bean.user.User;

import java.util.Date;

/**
 * User：zhouchen
 * Time: 2020/4/3  16:52
 * Description:
 */
public class Comment {
    //用户信息
    private User user;
    //评论分数
    private Double score;
    //评论id
    private Integer id;
    //规格名
    private String specName;
    //规格id
    private Integer specId;
    //评论内容
    private String comment;
    //评论时间
    private String time;
    //用户的id
    private Integer userId;
    //商品id
    private Integer goodId;
    //用户名
    private String name;
    //订单id
    private Integer orderId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + user +
                ", score=" + score +
                ", id=" + id +
                ", specName='" + specName + '\'' +
                ", specId=" + specId +
                ", comment='" + comment + '\'' +
                ", time='" + time + '\'' +
                ", userId=" + userId +
                ", goodId=" + goodId +
                ", name='" + name + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
