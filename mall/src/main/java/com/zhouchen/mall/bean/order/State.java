package com.zhouchen.mall.bean.order;

/**
 * User：zhouchen
 * Time: 2020/4/3  12:55
 * Description:
 */
public class State {
    //订单状态id
    private int id;
    //订单状态名
    private String name;

    public State() {
    }

    public State(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
