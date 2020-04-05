package com.zhouchen.mall.bean.order;

import com.zhouchen.mall.bean.Good.Good;
import com.zhouchen.mall.bean.Good.Spec;
import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.enums.OrderState;

import java.util.Date;
import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/2  22:22
 * Description:
 */
public class Order {
    // 订单id
    private Integer id;
    //用户id
    private Integer userId;
    //商品id
    private Integer goodId;
    //商品名
    private String good;
    //商品数量
    private Integer goodNum;
    //规格名
    private String spec;
    //总价格
    private Double amount;
    //订单状态id
    private Integer stateId;
    //订单状态名
    private String state;
    //用户名
    private String name;
    //收货地址
    private String address;
    //用户信息
    private User user;
    //用户是否删除该订单标志
    private Integer deleteFlag;
    //订单更新时间
    private String updateTime;
    //该订单对应的商品的所有规格
    private List<Spec> specs;
    //订单所有状态
    private List<State> states;
    //当前订单状态
    private State curState;
    //当前订单商品规格
    private Spec curSpec;
    //当前订单规格id
    private Integer specId;
    //订单状态枚举
    private OrderState orderState;
    //商品信息
    private Good goods;
    //订单是否评论标志位
    private boolean hasComment;

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

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Spec> specs) {
        this.specs = specs;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public State getCurState() {
        return curState;
    }

    public void setCurState(State curState) {
        this.curState = curState;
    }

    public Spec getCurSpec() {
        return curSpec;
    }

    public void setCurSpec(Spec curSpec) {
        this.curSpec = curSpec;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Good getGoods() {
        return goods;
    }

    public void setGoods(Good goods) {
        this.goods = goods;
    }

    public boolean isHasComment() {
        return hasComment;
    }

    public void setHasComment(boolean hasComment) {
        this.hasComment = hasComment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodId=" + goodId +
                ", good='" + good + '\'' +
                ", goodNum=" + goodNum +
                ", spec='" + spec + '\'' +
                ", amount=" + amount +
                ", stateId=" + stateId +
                ", state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", user=" + user +
                ", deleteFlag=" + deleteFlag +
                ", udpateTime='" + updateTime + '\'' +
                ", specs=" + specs +
                ", states=" + states +
                ", curState=" + curState +
                ", curSpec=" + curSpec +
                ", specId=" + specId +
                ", orderState=" + orderState +
                ", goods=" + goods +
                ", hasComment=" + hasComment +
                '}';
    }
}
