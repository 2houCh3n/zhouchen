package com.zhouchen.mall.bean.Good;

/**
 * User：zhouchen
 * Time: 2020/4/2  12:20
 * Description:
 */
public class Type {
    //类目id
    private Integer id;
    //类目名称
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
