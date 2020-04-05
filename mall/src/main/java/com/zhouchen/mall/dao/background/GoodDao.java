package com.zhouchen.mall.dao.background;

import com.zhouchen.mall.bean.Good.Good;
import com.zhouchen.mall.bean.Good.Spec;
import com.zhouchen.mall.bean.Good.Type;

import java.math.BigInteger;
import java.util.List;

public interface GoodDao {
    Type getType(String name);

    void addType(Type type);

    List<Type> getTypes();

    void addGood(Good good);

    void deleteType(int typeId);

    void deleteGoods(int typeId);

    List<Good> getGoodsByType(int typeId);

    Good getGood(int id);

    List<Spec> getSpecs(int goodId);

    void deleteSpecs(int goodId);

    void deleteGood(int id);

    void deleteSpec(Spec spec);

    Spec getSpec(String specName, Integer goodId);

    void addSpec(Spec spec);

    void updateGood(Good good);

    int queryLastId();

    Spec getSpec(Integer specId);

    List<Good> getAllGoods();

    void updateSpec(Spec spec);

    List<Good> getGoodsByName(String condition);
}
