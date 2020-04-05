package com.zhouchen.mall.dao.background.impl;

import com.alibaba.druid.util.StringUtils;
import com.zhouchen.mall.bean.order.Order;
import com.zhouchen.mall.bean.order.QueryCondition;
import com.zhouchen.mall.bean.order.State;
import com.zhouchen.mall.dao.background.OrderDao;
import com.zhouchen.mall.enums.OrderState;
import com.zhouchen.mall.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User：zhouchen
 * Time: 2020/4/2  11:08
 * Description:
 */
public class OrderDaoImpl implements OrderDao {
    /**
     * 动态拼接sql语句，然后从orders表中查询数据，以list形式返回
     * @param condition
     * @return
     */
    @Override
    public List<Order> ordersByPage(QueryCondition condition) {
        Map<String, Object> map = sqlJoin(condition);
        List<Object> params = (List<Object>) map.get("params");
        String sql = "select * from orders where 1 = 1 " + (String)(map.get("sql")) + " order by updateTime asc " + " limit ? offset ?";
        params.add(condition.getPageSize());
        params.add((condition.getCurrentPage() - 1) * condition.getPageSize());
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Order> orders = null;
        try {
            orders = runner.query(
                    sql,
                    new BeanListHandler<>(Order.class),
                    params.toArray()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * 获取符合条件的所有数据个数
     * @param condition
     * @return
     */
    @Override
    public int getTotalCount(QueryCondition condition) {
        Map<String, Object> map = sqlJoin(condition);
        String sql = (String) map.get("sql");
        List<Object> params = (List<Object>) map.get("params");
        sql = "select count(id) from orders where 1 = 1 " + sql;
        Integer total = null;
        if (params.size() == 0) {
            total = getTotalCount(sql);
            return total;
        }
        total = getTotalCount(sql, params);
        return total;
    }

    /**
     * 从orders表中删除指定的订单
     * @param orderId
     */
    @Override
    public void deleteOrder(int orderId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "update from orders where id=?",
                    orderId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从orders表中获取指定订单的详细信息
     * @param orderId
     * @return
     */
    @Override
    public Order getOrderInfo(int orderId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Order order = null;
        try {
            order = runner.query(
                    "select * from orders where id=?",
                    new BeanHandler<>(Order.class),
                    orderId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    /**
     * 从states表中获取所有的订单状态
     * @return
     */
    @Override
    public List<State> getStates() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<State> states = null;
        try {
            states = runner.query(
                    "select * from states",
                    new BeanListHandler<>(State.class)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return states;
    }

    /**
     * 用新的order来更新orders表中原有的对应的订单
     * @param order
     */
    @Override
    public void changeOrder(Order order) {
        QueryRunner runner = new QueryRunner( DruidUtils.getDataSource());
        try {
            runner.update(
                    "update orders set spec=?,goodNum=?,amount=?,stateId=?,state=?,updateTime=NOW() where id=?",
                    order.getSpec(),
                    order.getGoodNum(),
                    order.getAmount(),
                    order.getStateId(),
                    OrderState.value(order.getStateId()),
                    order.getId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从states表中获取指定的订单状态
     * @param stateId
     * @return
     */
    @Override
    public State getState(Integer stateId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        State state = null;
        try {
            state = runner.query(
                    "select * from states where id=?",
                    new BeanHandler<>(State.class),
                    stateId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state;
    }

    /**
     * 向orders表中添加新的订单
     * @param order
     */
    @Override
    public void addOrder(Order order) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "insert into orders (userId,goodId,goodNum,spec,amount,stateId,state,createTime,updateTime) values (?,?,?,?,?,?,?,NOW(),NOW())",
                    order.getUserId(),
                    order.getGoodId(),
                    order.getGoodNum(),
                    order.getSpec(),
                    order.getAmount(),
                    order.getStateId(),
                    OrderState.value(order.getStateId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户id和订单状态从orders表中获取相应的订单
     * @param stateId
     * @param id
     * @return
     */
    @Override
    public List<Order> getOrdersByState(int stateId, Integer id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Order> orders = null;
        try {
            orders = runner.query(
                    "select * from orders where stateId=? and userId=? and deleteFlag=0 order by createTime desc",
                    new BeanListHandler<>(Order.class),
                    stateId,
                    id
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * 获取指定用户的所有订单
     * @param id
     * @return
     */
    @Override
    public List<Order> getOrdersByState(Integer id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Order> orders = null;
        try {
            orders = runner.query(
                    "select * from orders where userId=? and deleteFlag=0 order by createTime desc",
                    new BeanListHandler<>(Order.class),
                    id
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * 用户从orders表中删除指定订单，考虑到实际需求，用户只能逻辑删除订单
     * 置deleteFlag标志位为true即可
     * @param orderId
     */
    @Override
    public void deleteOrderOfUser(int orderId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "update orders set deleteFlag=1,updateTime=NOW() where id=?",
                    orderId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更改订单的状态
     * @param order
     * @param stateId
     */
    @Override
    public void changeState(Order order, int stateId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "update orders set goodNum=?,amount=?,stateId=?,state=?,updateTime=NOW() where id=?",
                    order.getGoodNum(),
                    order.getAmount(),
                    stateId,
                    OrderState.value(stateId),
                    order.getId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置订单为以评论，置hasComment为1
     */
    @Override
    public void setHasComment(int orderId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update(
                    "update orders set hasComment=1,updateTime=NOW() where id=?",
                    orderId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Integer getTotalCount(String sql, List<Object> params) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Object total = null;
        try {
            total = runner.query(
                    sql,
                    new ScalarHandler<>(),
                    params.toArray()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(total));
    }

    private Integer getTotalCount(String sql) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Object total = null;
        try {
            total = runner.query(
                    sql,
                    new ScalarHandler<>()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(total));
    }

    /**
     * 动态拼接sql语句
     * @param condition
     * @return
     */
    private Map<String, Object> sqlJoin(QueryCondition condition) {
        String suffix = "";
        List<Object> params = new ArrayList<>();
        if(condition.getState() != -1){
            suffix = suffix + " and stateId = ?";
            params.add(condition.getState());
        }
        if(!StringUtils.isEmpty(condition.getId())){
            suffix += " and id = ?";
            params.add(condition.getId());
        }
        if(!StringUtils.isEmpty(condition.getMoneyLimit1())){
            suffix = suffix + " and amount >= ?";
            params.add(condition.getMoneyLimit1());
        }
        if(!StringUtils.isEmpty(condition.getMoneyLimit2())){
            suffix += " and amount <= ?";
            params.add(condition.getMoneyLimit2());
        }
        if(!(StringUtils.isEmpty(condition.getName()) && StringUtils.isEmpty(condition.getAddress()))){
            suffix += " and userId in (select id from users where 1=1 ";
            if(!StringUtils.isEmpty(condition.getAddress())){
                suffix += " and address like ?";
                params.add("%" + condition.getAddress() + "%");
            }
            if(!StringUtils.isEmpty(condition.getName())){
                suffix += " and nickname like ?";
                params.add("%" + condition.getName() + "%");
            }
            suffix += ")";
        }
        if (!StringUtils.isEmpty(condition.getGood())) {
            suffix += "and goodId in (select id from goods where nickname like ?)";
            params.add("%" + condition.getGood() + "%");
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("sql", suffix);
        map.put("params", params);
        return map;
    }
}

