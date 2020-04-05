package com.zhouchen.mall.bean.order;

/**
 * User：zhouchen
 * Time: 2020/4/2  22:02
 * Description:
 */
public class QueryCondition {
    //订单状态id
    private Integer state;
    //当前页数
    private Integer currentPage;
    //每页的数据个数
    private Integer pageSize;
    //金额下限
    private String moneyLimit1;
    //金额上限
    private String moneyLimit2;
    //商品名称
    private String good;
    //收货地址
    private String address;
    //用户名
    private String name;
    //订单id
    private String id;
    //所有的数据个数
    private Integer total;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getMoneyLimit1() {
        return moneyLimit1;
    }

    public void setMoneyLimit1(String moneyLimit1) {
        this.moneyLimit1 = moneyLimit1;
    }

    public String getMoneyLimit2() {
        return moneyLimit2;
    }

    public void setMoneyLimit2(String moneyLimit2) {
        this.moneyLimit2 = moneyLimit2;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "QueryCondition{" +
                "state=" + state +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", moneyLimit1='" + moneyLimit1 + '\'' +
                ", moneyLimit2='" + moneyLimit2 + '\'' +
                ", good='" + good + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", total=" + total +
                '}';
    }
}
