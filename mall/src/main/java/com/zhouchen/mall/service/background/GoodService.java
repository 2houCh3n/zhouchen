package com.zhouchen.mall.service.background;

import com.zhouchen.mall.bean.Good.Good;
import com.zhouchen.mall.bean.Good.Spec;
import com.zhouchen.mall.bean.Good.Type;

import java.util.List;
import java.util.Map;

public interface GoodService {
    int addType(Type type);

    List<Type> getTypes();

    void addGood(Good good);

    void deleteType(int typeId);

    List<Good> getGoodsByType(int typeId);

    Map<String, Object> getGoodInfo(int id);

    void deleteGood(int id);

    void updateGood(Good good);

    void delelteSpec(Spec spec);

    int addSpec(Spec spec);

    List<Good> getGoodsByName(String condition);
}
