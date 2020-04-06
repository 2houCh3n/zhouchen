package com.zhouchen.mall.controller.background;

import com.google.gson.Gson;
import com.zhouchen.mall.bean.order.Order;
import com.zhouchen.mall.bean.order.QueryCondition;
import com.zhouchen.mall.service.background.OrderService;
import com.zhouchen.mall.service.background.impl.OrderServiceImpl;
import com.zhouchen.mall.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/admin/order/*")
public class OrderServlet extends HttpServlet {
    OrderService orderService = new OrderServiceImpl();
    Gson gson = new Gson();

    /**
     * 对post请求进行分流
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回请求行中的资源名路径
        String requestURI = request.getRequestURI();
        String [] uris = requestURI.split("/");
        if ("ordersByPage".equals(uris[uris.length - 1])) {
            ordersByPage(request,response);
        }
        if ("changeOrder".equals(uris[uris.length - 1])) {
            changeOrder(request, response);
        }
    }

    /**
     * 1.将json字符串解析成QueryCondition对象
     * 2.根据QueryCondition对象从数据库获取相应的数据
     * 3.将获取到的数据写到响应体中
     * post
     * 请求体：
     * {
     * 	"state": -1,
     * 	"currentPage": 1,
     * 	"pagesize": 5,
     * 	"moneyLimit1": "",
     * 	"moneyLimit2": "",
     * 	"goods": "",
     * 	"address": "",
     * 	"name": "",
     * 	"id": ""
     * }
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": {
     * 		"total": 12,
     * 		"orders": [{
     * 			"id": 1774,
     * 			"userId": 1,
     * 			"goodsDetailId": 1266,
     * 			"goods": "香蕉",
     * 			"spec": "2KG",
     * 			"goodsNum": 1,
     * 			"amount": 10.0,
     * 			"stateId": 0,
     * 			"state": "未付款",
     * 			"user": {
     * 				"nickname": "admin",
     * 				"name": "admin",
     * 				"address": "admin",
     * 				"phone": "11111111111"
     *            }
     *        }]
     *    }
     * }
     * @param request
     * @param response
     */
    private void ordersByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        QueryCondition condition = gson.fromJson(requestBody, QueryCondition.class);

        Map<String, Object> map = orderService.ordersByPage(condition);
        HttpUtils.responseWriterTrue(map, response);
    }

    /**
     * 1.将json字符串解析成order对象
     * 2.用该json对象更新数据库中对应的数据
     * 3.将结果返回
     * post
     * 请求体：{"id":"100000001","stateId":0,"specId":21,"goodsNum":3}
     * 响应体：{"code": 0}
     * @param request
     * @param response
     */
    private void changeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Order order = gson.fromJson(requestBody,Order.class);
        int result = orderService.changeOrder(order);
        if (result == 2) {
            String message = "买家已经删除该订单，无法修改！";
            HttpUtils.responseWriterFalse(message, response);
        }
        if (result == 1) {
            String message = "买家已付款，无法修改数量和规格！";
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
        //返回请求行中的资源名路径
        String requestURI = request.getRequestURI();
        String [] uris = requestURI.split("/");
        if ("deleteOrder".equals(uris[uris.length - 1])) {
            deleteOrder(request, response);
        }
        if ("order".equals(uris[uris.length - 1])) {
            getOrderInfo(request, response);
        }
    }

    /**
     * 1.从数据库中删除指定订单
     * 2.将结果返回
     * get
     * 响应体：{"code":0}
     * @param request
     * @param response
     */
    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        int result = orderService.deleteOrder(orderId);
        if (result == 1) {
            String message = "该订单未完成，待订单完成后再删除！";
            HttpUtils.responseWriterFalse(message, response);
        }
        if (result == 0) {
            HttpUtils.responseWriterTrue(null, response);
        }
    }

    /**
     * 1.从数据中获取指定订单的详细信息，封装在order对象中
     * 2.将结果写入响应体返回
     * @param request
     * @param response
     */
    private void getOrderInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        Order order = orderService.getOrderInfo(orderId);
        HttpUtils.responseWriterTrue(order, response);
    }
}
