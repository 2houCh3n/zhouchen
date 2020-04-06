package com.zhouchen.mall.service.background;

import com.zhouchen.mall.bean.order.CartList;
import com.zhouchen.mall.bean.order.Order;
import com.zhouchen.mall.bean.order.QueryCondition;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String, Object> ordersByPage(QueryCondition condition);

    int deleteOrder(int orderId);

    Order getOrderInfo(int orderId);

    int changeOrder(Order order);

    int addOrder(Order order);

    List<Order> getOrdersByState(int stateId, String userName);

    void deleteOrderOfUser(int orderId);

    void settleAccounts(CartList cartList);

    void pay(int orderId);

    void confirmReceive(int orderId);
}
