package com.zhouchen.mall.bean.Good;

import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/2  13:02
 * Description:
 */
public class Good {
    //商品id
    private String id;
    //商品名称
    private String name;
    //所属类目名称
    private Integer typeId;
    //图片地址
    private String img;
    //商品描述
    private String desc;
    //所有规格最低价格
    private Double price;
    //商品数量
    private Integer stockNum;
    //所有规格
    private List<Spec> specList;
    //规格id
    private Integer specId;
    //规格ming
    private String spec;
    //该规格对应的单价
    private Double unitPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", img='" + img + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                ", stockNum=" + stockNum +
                ", specList=" + specList +
                ", specId=" + specId +
                ", spec='" + spec + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
