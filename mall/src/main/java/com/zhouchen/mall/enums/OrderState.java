package com.zhouchen.mall.enums;

/**
 * User：zhouchen
 * Time: 2020/4/2  22:30
 * Description:
 */
public enum OrderState {
    /**
     * 订单状态，未付款
     */
    UN_PAID(0, "未付款"),
    /**
     * 订单状态，未发货
     */
    UN_SHIP(1, "未发货"),
    /**
     * 订单状态，已发货
     */
    SHIPED(2, "已发货"),
    /**
     * 订单状态，已到货
     */
    RECEIVED(3, "已到货");

    public Integer name;

    public String value;

    OrderState(Integer name, String value) {
        this.name = name;
        this.value = value;
    }

    public static String value(Integer name) {
        for (OrderState value : OrderState.values()) {
            if (value.name.equals(name)) {
                return value.value;
            }
        }
        return null;
    }
}
