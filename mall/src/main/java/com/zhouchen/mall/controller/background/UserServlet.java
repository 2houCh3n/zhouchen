package com.zhouchen.mall.controller.background;

import com.google.gson.Gson;
import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.service.background.UserService;
import com.zhouchen.mall.service.background.impl.UserServiceImpl;
import com.zhouchen.mall.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/admin/user/*")
public class UserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();
    Gson gson = new Gson();

    /**
     * 对post请求进行分流操作
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * 对get请求进行分流操作
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
        if ("allUser".equals(uris[uris.length - 1])) {
            allUser(request, response);
        }
        if ("deleteUser".equals(uris[uris.length - 1])) {
            deleteUser(request, response);
        }
        if ("searchUser".equals(uris[uris.length - 1])) {
            searchUser(request, response);
        }
    }

    /**
     * 1.获取请求头中的请求参数，根据该参数，去数据库中查找指定的用户，用List封装
     * 2.将结果写入响应体
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": [{
     * 		"id": 1000001,
     * 		"email": "123456@qq.com",
     * 		"nickname": "user123456",
     * 		"pwd": "Qwer@123456",
     * 		"recipient": "严汉老师",
     * 		"address": "湖北省武汉市洪山区花山街道软件新城9号楼",
     * 		"phone": 18811111111
     *        }]
     * }
     * @param request
     * @param response
     */
    private void searchUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String word = request.getParameter("word");
        List<User> users = userService.searchUser(word);
        HttpUtils.responseWriterTrue(users, response);
    }

    /**
     * 1.获取请求头中的请求参数，根据该参数，去数据库中删除指定的用户
     * 2.返回结果
     * get
     * 响应体：{"code": 0}
     * @param request
     * @param response
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        //在数据库中删除该用户
        userService.deleteUser(id);
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 1.从数据库中获取所有的用户信息，用list封装
     * 2.将所有的用户信息写入响应体
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": [{
     * 		"id": 1000001,
     * 		"email": "123456@qq.com",
     * 		"nickname": "user123456",
     * 		"pwd": "Qwer@123456",
     * 		"recipient": "严汉老师",
     * 		"address": "湖北省武汉市洪山区花山街道软件新城9号楼",
     * 		"phone": 18811111111
     *        }]
     * }
     * @param request
     * @param response
     */
    private void allUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = userService.allUser();
        HttpUtils.responseWriterTrue(users, response);
    }
}
