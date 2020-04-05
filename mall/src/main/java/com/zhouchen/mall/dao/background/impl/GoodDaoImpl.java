package com.zhouchen.mall.dao.background.impl;

import com.zhouchen.mall.bean.Good.Good;
import com.zhouchen.mall.bean.Good.Spec;
import com.zhouchen.mall.bean.Good.Type;
import com.zhouchen.mall.dao.background.GoodDao;
import com.zhouchen.mall.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 * User：zhouchen
 * Time: 2020/4/2  11:05
 * Description:
 */
public class GoodDaoImpl implements GoodDao {
    /**
     * 根据指定的类目名，去types表中查找数据，将结果封装在type对象中返回
     * @param name
     * @return
     */
    @Override
    public Type getType(String name) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Type type = null;
        try {
            type = runner.query(
                    "select * from types where name=?",
                    new BeanHandler<>(Type.class),
                    name
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * 向types表中添加新的类目
     * @param type
     */
    @Override
    public void addType(Type type) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "insert into types (name) values (?)",
                    type.getName()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从types表中获取所有的类目信息，封装在list中返回
     * @return
     */
    @Override
    public List<Type> getTypes() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Type> types = null;
        try {
            types = runner.query(
                    "select * from types",
                    new BeanListHandler<>(Type.class)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    /**
     * 向goods表中添加新的商品
     * @param good
     */
    @Override
    public void addGood(Good good) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "insert into goods (img,`name`,`desc`,typeId,stockNum, price) values (?,?,?,?,?,?)",
                    good.getImg(),
                    good.getName(),
                    good.getDesc(),
                    good.getTypeId(),
                    good.getStockNum(),
                    good.getPrice()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  从types表中删除指定类目
     * @param typeId
     */
    @Override
    public void deleteType(int typeId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "delete from types where id=?",
                    typeId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除属于指定类目的所有商品
     * @param typeId
     */
    @Override
    public void deleteGoods(int typeId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "delete from goods where typeId=?",
                    typeId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从goods表中获取指定typeid的商品，并将结果返回
     * @param typeId
     * @return
     */
    @Override
    public List<Good> getGoodsByType(int typeId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Good> goods = null;
        try {
            goods = runner.query(
                    "select * from goods where typeId=?",
                    new BeanListHandler<>(Good.class),
                    typeId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    /**
     * 从goods表中根据指定id获取相应的商品详细信息，并返回
     * @param id
     * @return
     */
    @Override
    public Good getGood(int id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Good good = null;
        try {
            good = runner.query(
                    "select * from goods where id=?",
                    new BeanHandler<>(Good.class),
                    id
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return good;
    }

    /**
     * 从specs表中根据指定商品id获取相应的规格，以list形式返回
     * @param goodId
     * @return
     */
    @Override
    public List<Spec> getSpecs(int goodId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Spec> specs = null;
        try {
            specs = runner.query(
                    "select * from specs where goodId=?",
                    new BeanListHandler<>(Spec.class),
                    goodId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specs;
    }

    /**
     * 从specs表中删除指定goodId的数据
     * @param goodId
     */
    @Override
    public void deleteSpecs(int goodId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "delete from specs where goodId=?",
                    goodId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从goods表中删除指定的商品
     * @param id
     */
    @Override
    public void deleteGood(int id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "delete from goods where id=?",
                    id
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从specs表中删除指定的规格
     * @param spec
     */
    @Override
    public void deleteSpec(Spec spec) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "delete from specs where specName=? and goodId=?",
                    spec.getSpecName(),
                    spec.getGoodId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从specs表中获取指定商品id和规格名称的规格详细信息
     * @param specName
     * @param goodId
     * @return
     */
    @Override
    public Spec getSpec(String specName, Integer goodId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Spec spec = null;
        try {
            spec = runner.query(
                    "select * from specs where specName=? and goodId=?",
                    new BeanHandler<>(Spec.class),
                    specName,
                    goodId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spec;
    }

    /**
     * 将新的spec添加到specs表中
     * @param spec
     */
    @Override
    public void addSpec(Spec spec) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "insert into specs (specName, stockNum, unitPrice, goodId) values (?,?,?,?)",
                    spec.getSpecName(),
                    spec.getStockNum(),
                    spec.getUnitPrice(),
                    spec.getGoodId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用指定的商品信息来更新goods表中对应的商品
     * @param good
     */
    @Override
    public void updateGood(Good good) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "update goods set img=?,`name`=?,`desc`=?,typeId=?,price=?,stockNum=? where id=?",
                    good.getImg(),
                    good.getName(),
                    good.getDesc(),
                    good.getTypeId(),
                    good.getPrice(),
                    good.getStockNum(),
                    good.getId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取goods表中最后一行的商品
     * @return
     */
    @Override
    public int queryLastId() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        BigInteger query = null;
        try {
            query = runner.query("select last_insert_id()", new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query.intValue();
    }

    /**
     * 根据指定的规格id从specs表中获取指定的规格信息
     * @param specId
     * @return
     */
    @Override
    public Spec getSpec(Integer specId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Spec spec = null;
        try {
            spec = runner.query(
                    "select * from specs where id=?",
                    new BeanHandler<>(Spec.class),
                    specId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spec;
    }

    /**
     * 从goods表中获取所有的商品，以list形式返回
     * @return
     */
    @Override
    public List<Good> getAllGoods() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Good> goods = null;
        try {
            goods = runner.query(
                    "select * from goods",
                    new BeanListHandler<>(Good.class)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    /**
     * 更新specs表中的数据
     * @param spec
     */
    @Override
    public void updateSpec(Spec spec) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "update specs set specName=?,stockNum=?,unitPrice=? where id=?",
                    spec.getSpecName(),
                    spec.getStockNum(),
                    spec.getUnitPrice(),
                    spec.getId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从goods表中模糊查询指定的商品信息，以list形式返回
     * @param condition
     * @return
     */
    @Override
    public List<Good> getGoodsByName(String condition) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Good> goods = null;
        try {
            goods = runner.query(
                    "select * from goods where name like ?",
                    new BeanListHandler<>(Good.class),
                    "%" + condition + "%"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

}
