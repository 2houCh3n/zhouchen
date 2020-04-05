package com.zhouchen.mall.controller.front;

import com.google.gson.Gson;
import com.zhouchen.mall.bean.order.CartList;
import com.zhouchen.mall.bean.order.Order;
import com.zhouchen.mall.service.background.OrderService;
import com.zhouchen.mall.service.background.impl.OrderServiceImpl;
import com.zhouchen.mall.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/mall/order/*")
public class OrderServlet extends HttpServlet {
    Gson gson = new Gson();
    OrderService orderService = new OrderServiceImpl();
    /**
     * 对post请求进行分流
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String [] uris = requestURI.split("/");
        if ("addOrder".equals(uris[uris.length - 1])) {
            addOrder(request, response);
        }
        if ("settleAccounts".equals(uris[uris.length - 1])) {
            settleAccounts(request, response);
        }
    }

    /**
     * 将购物车里的订单全部付款
     * 将结果返回
     * post
     * 请求体：{
     * 	"cartList": [{
     * 		"id": 1815,
     * 		"goodsNum": 1,
     * 		"amount": 432
     *        }]
     * }
     * @param request
     * @param response
     */
    private void settleAccounts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        CartList cartList = gson.fromJson(requestBody, CartList.class);
        orderService.settleAccounts(cartList);
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 1.将json字符串解析成order对象
     * 2.将该对象添加到数据库中
     * 3.返回结果
     * post
     * 请求体：{"token":"NoUtopin","goodsDetailId":1436,"state":0,"num":3,"amount":936}
     * 响应体：{"code":0}
     * @param request
     * @param response
     */
    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Order order = gson.fromJson(requestBody, Order.class);

        int result = orderService.addOrder(order);
        if (result == 1) {
            String message = "库存量不足！";
            HttpUtils.responseWriterFalse(message, response);
        }
        if (result == 0) {
            HttpUtils.responseWriterTrue(null, response);
        }
    }

    /**
     * 对get请求进行分流
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String [] uris = requestURI.split("/");

        if ("getOrderByState".equals(uris[uris.length - 1])) {
            getOrdersByState(request, response);
        }
        if ("deleteOrder".equals(uris[uris.length - 1])) {
            deleteOrder(request, response);
        }
        if ("pay".equals(uris[uris.length - 1])) {
            pay(request, response);
        }
        if ("confirmReceive".equals(uris[uris.length - 1])) {
            confirmReceive(request, response);
        }
    }

    /**
     * 确认收货，置订单状态为已收货即可
     * get
     * @param request
     * @param response
     */
    private void confirmReceive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        orderService.confirmReceive(orderId);
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 对订单付款，将订单状态置为已付款即可
     * @param request
     * @param response
     */
    private void pay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        orderService.pay(orderId);
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 1.用户去数据库删除订单（逻辑删除）
     * 2.将结果返回
     * @param request
     * @param response
     */
    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        orderService.deleteOrderOfUser(orderId);
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 1.从数据库中读取指定的订单，以list形式返回
     * 2.将结果返回
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": [{
     * 		"id": 1798,
     * 		"state": 0,
     * 		"goodsNum": 1,
     * 		"amount": 1299.0,
     * 		"goodsDetailId": 1386,
     * 		"createtime": "2020-04-03 21:19:08",
     * 		"hasComment": false,
     * 		"goods": {
     * 			"id": 534,
     * 			"img": "http://115.29.141.32:8084/static/image/1585744787830timg (1).jpg",
     * 			"name": "球鞋",
     * 			"goodsDetailId": 1386,
     * 			"spec": "GS",
     * 			"unitPrice": 1299.0
     *        }
     *    }]
     * }
     * @param request
     * @param response
     */
    private void getOrdersByState(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int stateId = Integer.parseInt(request.getParameter("state"));
        String userName = request.getParameter("token");

        List<Order> orders = orderService.getOrdersByState(stateId, userName);
        HttpUtils.responseWriterTrue(orders, response);
    }
}
