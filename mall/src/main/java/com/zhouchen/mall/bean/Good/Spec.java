package com.zhouchen.mall.bean.Good;

/**
 * User：zhouchen
 * Time: 2020/4/2  13:06
 * Description:
 */
public class Spec {
    /**
     * 规格id
     */
    private String id;
    /**
     * 规格名称
     */
    private String specName;
    /**
     * 库存数量
     */
    private String stockNum;
    /**
     * 单价
     */
    private String unitPrice;
    /**
     * 对应的商品的id
     */
    private String goodId;

    public Spec(){}

    public Spec(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    @Override
    public String toString() {
        return "Spec{" +
                "id=" + id +
                ", specName='" + specName + '\'' +
                ", stockNum='" + stockNum + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", goodId='" + goodId + '\'' +
                '}';
    }
}
