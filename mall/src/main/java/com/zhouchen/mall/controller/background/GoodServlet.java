package com.zhouchen.mall.controller.background;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhouchen.mall.bean.Good.Good;
import com.zhouchen.mall.bean.Good.Spec;
import com.zhouchen.mall.bean.Good.Type;
import com.zhouchen.mall.bean.Result;
import com.zhouchen.mall.service.background.GoodService;
import com.zhouchen.mall.service.background.impl.GoodServiceImpl;
import com.zhouchen.mall.util.FileUploadUtils;
import com.zhouchen.mall.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/admin/good/*")
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
        //返回请求行中的资源名路径
        String requestURI = request.getRequestURI();
        String [] uris = requestURI.split("/");
        if ("imgUpload".equals(uris[uris.length - 1])) {
            imgUpload(request, response);
        }
        if ("addType".equals(uris[uris.length - 1])) {
            addType(request, response);
        }
        if ("addGood".equals(uris[uris.length - 1])) {
            addGood(request, response);
        }
        if ("updateGood".equals(uris[uris.length - 1])) {
            updateGood(request, response);
        }
        if ("addSpec".equals(uris[uris.length - 1])) {
            addSpec(request, response);
        }
        if ("deleteSpec".equals(uris[uris.length - 1])) {
            deleteSpec(request, response);
        }
    }

    /**
     * 将图片上传到服务器，然后返回图片地址
     * @param request
     * @param response
     * @throws IOException
     */
    private void imgUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = FileUploadUtils.parseRequest(request);
        String file = (String) map.get("file");
        file = "http://" + request.getServerName() + ":" + request.getServerPort() + file;
        HttpUtils.responseWriterTrue(file, response);
    }

    /**
     * 1.将json字符串解析成type对象
     * 2.将该type对象添加到数据库
     * 3.返回结果
     * post
     * 请求体：{name: "啥玩意"}
     * 响应体：{"code":0}
     * @param request
     * @param response
     */
    private void addType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = HttpUtils.getRequestBody(request);
        Type type = gson.fromJson(body, Type.class);

        int result = goodService.addType(type);
        if (result == 0) {
            HttpUtils.responseWriterTrue(null, response);
        }
        if (result == 1) {
            HttpUtils.responseWriterFalse("该种类已经存在！", response);
        }
    }

    /**
     * 1.将json字符串解析成good对象
     * 2.将该good对象添加到数据库
     * 3.返回结果
     * post
     * 请求体：
     * {
     * 	"name": "啥玩意",
     * 	"typeId": "190",
     * 	"img": "/static/image/1585802950011157123045512415669928366681566992814(1).jpg",
     * 	"desc": "fsdfsd",
     * 	"specList": [{
     * 		"specName": "M",
     * 		"stockNum": "1",
     * 		"unitPrice": "1"
     *        }, {
     * 		"specName": "M",
     * 		"stockNum": "0",
     * 		"unitPrice": "3123"
     *    }]
     * }
     * 响应体：{"code":0}
     * @param request
     * @param response
     */
    private void addGood(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Good good = gson.fromJson(requestBody, Good.class);
        if (!checkGood(good, response)) {
            return;
        }
        try {
            goodService.addGood(good);
        } catch (NumberFormatException e) {
            String message = "库存量或者价格格式不正确！";
            HttpUtils.responseWriterFalse(message, response);
            return;
        }
        HttpUtils.responseWriterTrue(null, response);
    }
    /**
     * 1.将json字符串解析成good对象
     * 2.用该good对象更新数据库中对应的数据
     * 3.返回结果
     * post
     * 请求体：{
     * 	"id": "475",
     * 	"name": "半身裙",
     * 	"typeId": 190,
     * 	"img": "http://115.29.141.32:8084/static/image/1570850192433156699448698020190828201124.jpg",
     * 	"desc": "新品上市",
     * 	"specList": [{
     * 		"id": 1230,
     * 		"specName": "x",
     * 		"stockNum": 977,
     * 		"unitPrice": 100
     *        }, {
     * 		"id": 1231,
     * 		"specName": "l",
     * 		"stockNum": 995,
     * 		"unitPrice": 110
     *    }, {
     * 		"id": 1294,
     * 		"specName": "12",
     * 		"stockNum": 3241,
     * 		"unitPrice": 102
     *    }]
     * }
     * 响应体：{"code": 0}
     * @param request
     * @param response
     */
    private void updateGood(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Good good = gson.fromJson(requestBody, Good.class);
        if (!checkGood(good, response)) {
            return;
        }
        try {
            goodService.updateGood(good);
        } catch (NumberFormatException e) {
            String message = "价格或者库存格式不正确！";
            HttpUtils.responseWriterFalse(message, response);
            return;
        }
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 1.将json字符串解析成spec对象
     * 2.先对该spec对象的内容进行判断，看是否符合要求
     * 3.将该spec对象添加到数据库
     * 4.返回结果
     * post
     * 请求体;{"goodsId":"475","specName":"M","stockNum":"321","unitPrice":"432"}
     * 响应体：{"code":0,"data":{"goodsId":"475","specName":"M","stockNum":"321","unitPrice":"432"}}
     * @param request
     * @param response
     */
    private void addSpec(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Spec spec = gson.fromJson(requestBody, Spec.class);
        try {
            Double.parseDouble(spec.getUnitPrice());
            Integer.parseInt(spec.getStockNum());
        } catch (NumberFormatException e) {
            String message = "库存量或者价格格式不正确！";
            HttpUtils.responseWriterFalse(message, response);
            return;
        }
        int result = goodService.addSpec(spec);
        if (result == 1) {
            String message = "该规格已经存在！";
            HttpUtils.responseWriterFalse(message, response);
        }
        if (result == 0) {
            HttpUtils.responseWriterTrue(spec, response);
        }
    }

    /**
     * 1.将json字符串解析成spec对象
     * 2.根据该spec对象去数据库删除对应的规格
     * 3.返回结果
     * post
     * 请求体：{"goodsId":"475","specName":"l"}
     * 响应体：{"code":0}
     * @param request
     * @param response
     */
    private void deleteSpec(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Spec spec = gson.fromJson(requestBody, Spec.class);
        goodService.delelteSpec(spec);
        HttpUtils.responseWriterTrue(null, response);
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
        if ("getTypes".equals(uris[uris.length - 1])) {
            getTypes(request, response);
        }
        if ("deleteType".equals(uris[uris.length - 1])) {
            deleteType(request, response);
        }
        if ("getGoodsByType".equals(uris[uris.length - 1])) {
            getGoodsByType(request, response);
        }
        if ("getGoodsInfo".equals(uris[uris.length - 1])) {
            getGoodInfo(request, response);
        }
        if ("deleteGood".equals(uris[uris.length - 1])) {
            deleteGood(request,response);
        }
    }

    /**
     * 从数据库中获取所有的类目，将结果封装在list中返回
     * get
     * 响应体：{"code":0,"data":[{"id":190,"name":"男装"}]}
     * @param request
     * @param response
     */
    private void getTypes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Type> types = goodService.getTypes();
        HttpUtils.responseWriterTrue(types, response);
    }

    /**
     * 1.从数据库删除指定的类目
     * 2.返回结果
     * get
     * 响应体：{"code": 0}
     * @param request
     * @param response
     */
    private void deleteType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int typeId = Integer.parseInt(request.getParameter("typeId"));
        goodService.deleteType(typeId);
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 1.从数据库中获取指定类目下的所有商品（List形式）
     * 2.将结果返回
     * get
     * 响应体：{
     *              "code":0,"
     *              data":[{
     *                  "id":475,
     *                  "img":"http://115.29.141.32:8084/static/image/1570850192433156699448698020190828201124.jpg",
     *                  "name":"半身裙",
     *                  "price":100.0,
     *                  "typeId":190,
     *                  "stockNum":5212
     *                  }]
     *          }
     * @param request
     * @param response
     */
    private void getGoodsByType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int typeId = Integer.parseInt(request.getParameter("typeId"));
        List<Good> goods = goodService.getGoodsByType(typeId);
        HttpUtils.responseWriterTrue(goods, response);
    }

    /**
     * 1.从数据库中获取指定商品详细信息
     * 2.将结果返回
     * get
     * 响应体：{"code":0,
     *              "data":{
     *                  "specs":[{"id":1230,"specName":"x","stockNum":977,"unitPrice":100.0},{"id":1231,"specName":"l","stockNum":995,"unitPrice":110.0},{"id":1294,"specName":"12","stockNum":3241,"unitPrice":102.0}],
     *                  "goods":{"id":475,"img":"http://115.29.141.32:8084/static/image/1570850192433156699448698020190828201124.jpg","name":"半身裙","desc":"新品上市","typeId":190,"unitPrice":0.0}
     *                 }
     *         }
     * @param request
     * @param response
     */
    private void getGoodInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Map<String, Object> map = goodService.getGoodInfo(id);
        HttpUtils.responseWriterTrue(map, response);
    }

    /**
     * 1.从数据库中删除指定的商品
     * 2.将结果写入响应体
     * get
     * 响应体：{"code": 0}
     * @param request
     * @param response
     */
    private void deleteGood(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        goodService.deleteGood(id);
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 检查商品的信息是否满足要求
     * @param good
     */
    private boolean checkGood(Good good, HttpServletResponse response) throws IOException {
        if (StringUtils.isEmpty(good.getName())) {
            String message = "商品名称不能为空！";
            HttpUtils.responseWriterFalse(message, response);
            return false;
        }
        if (StringUtils.isEmpty(good.getImg())) {
            String message = "图片不能为空！";
            HttpUtils.responseWriterFalse(message, response);
            return false;
        }
        List<Spec> specs = good.getSpecList();
        for (int i = 0; i < specs.size(); i++) {
            for (int j = i + 1; j < specs.size(); j++) {
                if (specs.get(i).getSpecName().equals(specs.get(j).getSpecName())) {
                    String message = "规格名称不能重复！";
                    HttpUtils.responseWriterFalse(message, response);
                    return false;
                }
            }
        }
        return true;
    }
}
