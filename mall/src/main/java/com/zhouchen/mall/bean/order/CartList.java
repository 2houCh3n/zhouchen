package com.zhouchen.mall.bean.order;

import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/4  7:45
 * Description:
 */
public class CartList {
    //所有订单
    private List<Order> cartList;
    public List<Order> getCartList() {
        return cartList;
    }

    public void setCartList(List<Order> cartList) {
        this.cartList = cartList;
    }

    @Override
    public String toString() {
        return "CartList{" +
                "cartList=" + cartList +
                '}';
    }
}
