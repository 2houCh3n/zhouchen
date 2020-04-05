package com.zhouchen.mall.controller.front;

import com.google.gson.Gson;
import com.zhouchen.mall.bean.Good.Good;
import com.zhouchen.mall.bean.Good.Spec;
import com.zhouchen.mall.bean.Good.Type;
import com.zhouchen.mall.service.background.GoodService;
import com.zhouchen.mall.service.background.impl.GoodServiceImpl;
import com.zhouchen.mall.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/mall/good/*")
public class GoodServlet extends HttpServlet {
    GoodService goodService = new GoodServiceImpl();
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
        if ("getType".equals(uris[uris.length - 1])) {
            getTypes(request, response);
        }
        if ("getGoodsByType".equals(uris[uris.length - 1])) {
            getGoodsByType(request, response);
        }
        if ("getGoodInfo".equals(uris[uris.length - 1])) {
            getGoodInfo(request, response);
        }
        if ("searchGoods".equals(uris[uris.length - 1])) {
            searchGoods(request, response);
        }
    }

    /**
     * 根据搜索条件从数据库中查询所有的商品信息
     * get
     * 响应体：{
     * 	"code": 0,
     * 	"data": [{
     * 		"id": 542,
     * 		"img": "http://115.29.141.32:8084/static/image/1585802950011157123045512415669928366681566992814(1).jpg",
     * 		"name": "啥玩意",
     * 		"price": 1.0,
     * 		"typeId": 190
     *        }]
     * }
     * @param request
     * @param response
     */
    private void searchGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String condition = request.getParameter("keyword");
        List<Good> goods = goodService.getGoodsByName(condition);
        HttpUtils.responseWriterTrue(goods, response);
    }

    /**
     * 1.从数据库获取指定的商品信息
     * 2.将结果返回
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": {
     * 		"img": "http://115.29.141.32:8084/static/image/1570850192433156699448698020190828201124.jpg",
     * 		"name": "半身裙",
     * 		"desc": "新品上市",
     * 		"typeId": 190,
     * 		"specs": [{
     * 			"id": 1230,
     * 			"specName": "x",
     * 			"stockNum": 977,
     * 			"unitPrice": 100.0
     *                }],
     * 		"unitPrice": 0.0* 	}
     * }
     * @param request
     * @param response
     */
    private void getGoodInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int goodId = Integer.parseInt(request.getParameter("id"));
        Map<String, Object> map = goodService.getGoodInfo(goodId);
        Good good = (Good) map.get("goods");
        good.setSpecList((List<Spec>) map.get("specs"));
        HttpUtils.responseWriterTrue(good, response);
    }

    /**
     * 1.从数据库中获取指定类目下的所有的商品，以list形式返回
     * 2.将结果返回
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": [{
     * 		"id": 475,
     * 		"img": "http://115.29.141.32:8084/static/image/1570850192433156699448698020190828201124.jpg",
     * 		"name": "半身裙",
     * 		"price": 100.0,
     * 		"typeId": 190,
     * 		"stockNum": 1530
     *        }]
     * }
     * @param request
     * @param response
     */
    private void getGoodsByType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int typeId = Integer.parseInt(request.getParameter("typeId"));
        List<Good> goods = goodService.getGoodsByType(typeId);
        HttpUtils.responseWriterTrue(goods, response);
    }

    /**
     * 1.从数据库获取所有的商品类目，以list形式返回
     * 2.将结果返回
     * get
     * 响应体：{"code":0,"data":[{"id":190,"name":"男装"}]}
     * @param request
     * @param response
     */
    private void getTypes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Type> types = goodService.getTypes();
        HttpUtils.responseWriterTrue(types, response);
    }
}
