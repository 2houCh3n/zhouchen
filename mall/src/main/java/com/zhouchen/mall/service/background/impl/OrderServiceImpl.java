package com.zhouchen.mall.service.background.impl;

import com.zhouchen.mall.bean.Good.Good;
import com.zhouchen.mall.bean.Good.Spec;
import com.zhouchen.mall.bean.order.CartList;
import com.zhouchen.mall.bean.order.Order;
import com.zhouchen.mall.bean.order.QueryCondition;
import com.zhouchen.mall.bean.order.State;
import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.dao.background.GoodDao;
import com.zhouchen.mall.dao.background.OrderDao;
import com.zhouchen.mall.dao.background.UserDao;
import com.zhouchen.mall.dao.background.impl.GoodDaoImpl;
import com.zhouchen.mall.dao.background.impl.OrderDaoImpl;
import com.zhouchen.mall.dao.background.impl.UserDaoImpl;
import com.zhouchen.mall.service.background.GoodService;
import com.zhouchen.mall.service.background.OrderService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User：zhouchen
 * Time: 2020/4/2  11:08
 * Description:
 */
public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();

    /**
     * 1.首先获取所有的符合条件的订单数量
     * 2.再根据每页的数据个数以及页数去数据库获取相应的数据，以list形式返回
     * 3.将总订单数和数据以map返回
     * @param condition
     * @return
     */
    @Override
    public Map<String, Object> ordersByPage(QueryCondition condition) {
        int total = orderDao.getTotalCount(condition);
        List<Order> orders =  orderDao.ordersByPage(condition);
        Map<String, Object> map = new HashMap<>();
        for (Order order : orders) {
            UserDao userDao = new UserDaoImpl();
            GoodDao goodDao = new GoodDaoImpl();
            User user = userDao.getUser(order.getUserId());
            Good good = goodDao.getGood(order.getGoodId());
            order.setGood(good.getName());
            order.setUser(user);
        }
        map.put("total", total);
        map.put("orders", orders);
        return map;
    }

    @Override
    public void deleteOrder(int orderId) {
        orderDao.deleteOrder(orderId);
    }

    /**
     * 从数据库获取指定订单的详细信息
     * 1.先获取该订单
     * 2.通过该订单获取该订单相应的用户信息、商品信息、规格以及订单状态
     * 3.将结果返回
     * @param orderId
     * @return
     */
    @Override
    public Order getOrderInfo(int orderId) {
        //获取订单的详细信息
        Order order = orderDao.getOrderInfo(orderId);
        GoodDao goodDao = new GoodDaoImpl();
        //根据订单的商品id获取对应的商品的详细信息
        Good good = goodDao.getGood(order.getGoodId());
        order.setSpecs(goodDao.getSpecs(Integer.parseInt(good.getId())));
        order.setGood(good.getName());
        order.setStates(orderDao.getStates());
        order.setCurState(new State(order.getStateId()));
        Spec spec = goodDao.getSpec(order.getSpec(), order.getGoodId());
        order.setCurSpec(new Spec(spec.getId()));
        return order;
    }

    /**
     * 根据参数来修改数据库中的订单
     * 1.首先判断订单的是否被用户删除（逻辑），如果被删除，管理员无法修改，返回2
     * 2.其次判断该订单的状态，如果该订单的状态是未付款，那么管理员可以修改数目和规格
     *                         否则，无法修改数目和规格，并返回1
     * 3.判断完毕后，首先获取该订单对应的规格，修改对应的数据
     *               再获取该订单对应的商品，修改对应的数据
     *               最后再修改该订单
     * @param order
     */
    @Override
    public int changeOrder(Order order) {
        //判断订单的状态
        GoodDao goodDao = new GoodDaoImpl();
        Order resOrder = orderDao.getOrderInfo(order.getId());
        if (resOrder.getDeleteFlag() == 1) {
            //表示订单被用户逻辑删除了
            return 2;
        }
        if (resOrder.getStateId() != 0) {
            //订单状态不是未付款
            if (!(resOrder.getGoodNum().equals(order.getGoodNum())) || !(resOrder.getSpec().equals(goodDao.getSpec(order.getSpecId()).getSpecName()))) {
                //订单的商品数量和订单的规格发生了改变
                return 1;
            } else {
                //仅仅只有订单状态发生了改变
                resOrder.setStateId(order.getStateId());
                orderDao.changeOrder(resOrder);
            }
        } else {
            //订单状态是未付款
            resOrder.setGoodNum(order.getGoodNum());
            Spec spec = goodDao.getSpec(order.getSpecId());
            resOrder.setSpec(spec.getSpecName());
            resOrder.setAmount(Double.parseDouble(spec.getUnitPrice()) * order.getGoodNum());
            resOrder.setStateId(order.getStateId());
            orderDao.changeOrder(resOrder);
        }
        return 0;
    }

    /**
     * 向数据库中添加新的订单
     * 1.首先查看该商品对应规格的库存，如果订单商品个数大于库存，则返回1
     * 2.向数据库添加，同时在数据库减去相应的商品的库存以及对应的规格的库存，然后返回0
     * @param order
     * @return
     */
    @Override
    public int addOrder(Order order) {
        GoodDao goodDao = new GoodDaoImpl();
        UserDao userDao = new UserDaoImpl();
        Spec spec = goodDao.getSpec(order.getSpecId());
        User user = userDao.getUserByNickname(order.getName());
        //判断库存是否满足
        if (Integer.parseInt(spec.getStockNum()) < order.getGoodNum()) {
            return 1;
        }
        //更新相应的规格的库存
        spec.setStockNum(String.valueOf(Integer.parseInt(spec.getStockNum()) - order.getGoodNum()));
        goodDao.updateSpec(spec);
        //再更新相应的商品的库存
        Good good = goodDao.getGood(Integer.parseInt(spec.getGoodId()));
        List<Spec> specs = goodDao.getSpecs(Integer.parseInt(good.getId()));
        good.setSpecList(specs);
        goodDao.updateGood(good);
        //将用户、商品信息跟订单绑定起来，写入数据库
        order.setUserId(user.getId());
        order.setGoodId(Integer.parseInt(good.getId()));
        order.setSpec(spec.getSpecName());
        orderDao.addOrder(order);
        return 0;
    }

    /**
     * 从数据库获取指定用户的指定订单状态的订单
     * 1.首先根据用户名获取该用户的id
     * 2.再根据用户id和订单状态获取符合条件的订单，以list形式返回
     * @param stateId
     * @param userName
     * @return
     */
    @Override
    public List<Order> getOrdersByState(int stateId, String userName) {
        //获取用户id
        UserDao userDao = new UserDaoImpl();
        User user = userDao.getUserByNickname(userName);
        List<Order> orders = null;
        if (stateId == -1) {
            orders = orderDao.getOrdersByState(user.getId());
        } else {
            orders = orderDao.getOrdersByState(stateId, user.getId());
        }
        GoodDao goodDao = new GoodDaoImpl();
        for (Order order : orders) {
            Good good = goodDao.getGood(order.getGoodId());
            Spec spec = goodDao.getSpec(order.getSpec(), order.getGoodId());
            good.setSpecId(Integer.parseInt(spec.getId()));
            good.setSpec(order.getSpec());
            good.setUnitPrice(Double.parseDouble(spec.getUnitPrice()));
            order.setGoods(good);
        }
        return orders;
    }

    /**
     * 首先获取订单对应的商品信息
     * 根据下单数量更新规格以及商品库存
     * @param orderId
     */
    @Override
    public void deleteOrderOfUser(int orderId) {
        orderDao.deleteOrderOfUser(orderId);
        GoodDao goodDao = new GoodDaoImpl();
        Order order = orderDao.getOrderInfo(orderId);
        if (order.getStateId() != 3) {
            //该订单未完成，删除订单等同于退货退款，需要对后台商品库存进行更新
            Spec spec = goodDao.getSpec(order.getSpec(),order.getGoodId());
            Good good = goodDao.getGood(order.getGoodId());
            spec.setStockNum(String.valueOf(Integer.parseInt(spec.getStockNum()) + order.getGoodNum()));
            good.setStockNum(good.getStockNum() + order.getGoodNum());
            goodDao.updateSpec(spec);
            goodDao.updateGood(good);
        }
        orderDao.deleteOrderOfUser(orderId);
    }

    /**
     * 首先要从数据库中获取该订单相关的商品信息；
     * 根据下单数量更新规格以及商品库存
     * @param cartList
     */
    @Override
    public void settleAccounts(CartList cartList) {
        GoodDao goodDao = new GoodDaoImpl();
        for (Order order : cartList.getCartList()) {
            //获取订单的详细信息
            Order orderInfo = orderDao.getOrderInfo(order.getId());
            //更新规格以及商品库存
            Good good = goodDao.getGood(orderInfo.getGoodId());
            Spec spec = goodDao.getSpec(orderInfo.getSpec(), orderInfo.getGoodId());
            int goodNumChange = order.getGoodNum() - orderInfo.getGoodNum();
            spec.setStockNum(String.valueOf(Integer.parseInt(spec.getStockNum()) - goodNumChange));
            good.setStockNum(good.getStockNum() - goodNumChange);
            goodDao.updateSpec(spec);
            goodDao.updateGood(good);
            orderDao.changeState(order, 1);
        }
    }

    /**
     * 对订单进行付款
     * @param orderId
     */
    @Override
    public void pay(int orderId) {
        Order order = orderDao.getOrderInfo(orderId);
        orderDao.changeState(order, 1);
    }

    @Override
    public void confirmReceive(int orderId) {
        Order order = orderDao.getOrderInfo(orderId);
        orderDao.changeState(order, 3);
    }


}
