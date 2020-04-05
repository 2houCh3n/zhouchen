package com.zhouchen.mall.dao.background;

import com.zhouchen.mall.bean.order.Order;
import com.zhouchen.mall.bean.order.QueryCondition;
import com.zhouchen.mall.bean.order.State;

import java.util.List;

public interface OrderDao {
    List<Order> ordersByPage(QueryCondition condition);

    int getTotalCount(QueryCondition condition);

    void deleteOrder(int orderId);

    Order getOrderInfo(int orderId);

    List<State> getStates();

    void changeOrder(Order order);

    State getState(Integer stateId);

    void addOrder(Order order);

    List<Order> getOrdersByState(int stateId, Integer id);

    List<Order> getOrdersByState(Integer id);

    void deleteOrderOfUser(int orderId);

    void changeState(Order order, int stateId);

    void setHasComment(int orderId);
}
