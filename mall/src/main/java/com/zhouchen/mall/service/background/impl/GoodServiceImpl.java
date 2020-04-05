package com.zhouchen.mall.service.background.impl;

import com.zhouchen.mall.bean.Good.Good;
import com.zhouchen.mall.bean.Good.Spec;
import com.zhouchen.mall.bean.Good.Type;
import com.zhouchen.mall.dao.background.GoodDao;
import com.zhouchen.mall.dao.background.impl.GoodDaoImpl;
import com.zhouchen.mall.service.background.GoodService;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User：zhouchen
 * Time: 2020/4/2  11:05
 * Description:
 */
public class GoodServiceImpl implements GoodService {
    GoodDao goodDao = new GoodDaoImpl();
    /**
     * 向数据库中添加新的类目。
     * 在添加新类目之前，对要添加的类目先进行判断：
     * 若该类目名已经存在了，那么返回1
     * 若该类目名不存在，那么就成功添加，然后返回0
     * @param type
     * @return
     */
    @Override
    public int addType(Type type) {
        Type resType = goodDao.getType(type.getName());
        if (resType != null) {
            return 1;
        }
        goodDao.addType(type);
        return 0;
    }

    @Override
    public List<Type> getTypes() {
        return goodDao.getTypes();
    }

    @Override
    public void addGood(Good good) {
        List<Spec> specs = good.getSpecList();
        good.setStockNum(getStockNum(specs));
        good.setPrice(getMinPrice(specs));
        goodDao.addGood(good);
        int id = goodDao.queryLastId();
        for (Spec spec : specs) {
            spec.setGoodId(String.valueOf(id));
            goodDao.addSpec(spec);
        }
    }

    /**
     * 在数据库中删除指定的类目，
     * 在这之前首先要删除该类目下的所有的商品,
     * 然后在删除该类目
     * @param typeId
     */
    @Override
    public void deleteType(int typeId) {
        goodDao.deleteGoods(typeId);
        goodDao.deleteType(typeId);
    }

    @Override
    public List<Good> getGoodsByType(int typeId) {
        if (typeId == -1) {
            return goodDao.getAllGoods();
        }
        return goodDao.getGoodsByType(typeId);
    }

    /**
     * 从数据库中获取指定id的商品信息，以及该商品对应的所有规格
     * 以map形式返回
     * 1.先获取商品
     * 2.在获取该商品对应的规格
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getGoodInfo(int id) {
        Map<String, Object> map = new HashMap<>();
        Good good = goodDao.getGood(id);
        List<Spec> specs = goodDao.getSpecs(id);
        map.put("specs", specs);
        map.put("goods", good);
        return  map;
    }

    /**
     * 从数据库中删除指定商品
     * 1.首先删除该商品对应的所有的规格
     * 2.再去删除该商品
     * @param id
     */
    @Override
    public void deleteGood(int id) {
        goodDao.deleteSpecs(id);
        goodDao.deleteGood(id);
    }

    /**
     * 用新的商品信息来更新数据库中对应的商品
     * @param good
     */
    @Override
    public void updateGood(Good good) {
        List<Spec> specs = good.getSpecList();
        good.setPrice(getMinPrice(specs));
        good.setStockNum(getStockNum(specs));
        for (Spec spec : good.getSpecList()) {
            goodDao.updateSpec(spec);
        }
        goodDao.updateGood(good);
    }

    /**
     * 从数据库中删除指定规格，同时还要更新该规格对应的商品的库存
     * @param spec
     */
    @Override
    public void delelteSpec(Spec spec) {
        goodDao.deleteSpec(spec);
        //更新goods表中的库存信息
        Good good = goodDao.getGood(Integer.parseInt(spec.getGoodId()));
        List<Spec> specs = goodDao.getSpecs(Integer.parseInt(spec.getGoodId()));
        good.setStockNum(getStockNum(specs));
        goodDao.updateGood(good);
    }

    /**
     * 添加新的规格到数据库
     * 首先判断该规格名称是否在数据库存在：
     * 如果已经存在，则返回1
     * 如果不存在，则添加到数据库(同时还要更新goods表中该规格对应的商品的库存)
     * @param spec
     */
    @Override
    public int addSpec(Spec spec) {
        Spec resSpec = goodDao.getSpec(spec.getSpecName(), Integer.parseInt(spec.getGoodId()));
        if (resSpec != null) {
            //判断该规格是否已经存在
            return 1;
        }
        goodDao.addSpec(spec);
        //更新goods表中的商品的库存信息
        List<Spec> specs = goodDao.getSpecs(Integer.parseInt(spec.getGoodId()));
        Good good = goodDao.getGood(Integer.parseInt(spec.getGoodId()));
        good.setStockNum(getStockNum(specs));
        goodDao.updateGood(good);
        return 0;
    }

    /**
     *
     * @param condition
     * @return
     */
    @Override
    public List<Good> getGoodsByName(String condition) {
        return goodDao.getGoodsByName(condition);
    }

    /**
     * 获取规格序列中单价最小的规格价格
     * @param specs
     * @return
     */
    private double getMinPrice(List<Spec> specs) {
        double price = Double.parseDouble(specs.get(0).getUnitPrice());
        //从第二个开始遍历
        for (int i = 1; i < specs.size(); i++) {
            if(Double.parseDouble(specs.get(i).getUnitPrice()) < price){
                price = Double.parseDouble(specs.get(i).getUnitPrice());
            }
        }
        return price;
    }

    /**
     * 根据每个规格的商品个数获取商品的总数
     * @param specs
     * @return
     */
    private int getStockNum(List<Spec> specs) {
        int num = 0;
        for (Spec spec : specs) {
            num += Integer.parseInt(spec.getStockNum());
        }
        return num;
    }
}
