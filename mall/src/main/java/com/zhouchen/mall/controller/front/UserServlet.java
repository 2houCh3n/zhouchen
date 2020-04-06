package com.zhouchen.mall.controller.front;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.zhouchen.mall.bean.user.User;
import com.zhouchen.mall.bean.user.UserPwd;
import com.zhouchen.mall.service.background.UserService;
import com.zhouchen.mall.service.background.impl.UserServiceImpl;
import com.zhouchen.mall.util.FileUploadUtils;
import com.zhouchen.mall.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/mall/user/*")
public class UserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();
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
        String requestURI = request.getRequestURI();
        String [] uris = requestURI.split("/");
        if ("login".equals(uris[uris.length - 1])) {
            loginUser(request, response);
        }
        if ("signup".equals(uris[uris.length - 1])) {
            signupUser(request, response);
        }
        if ("updateUserData".equals(uris[uris.length - 1])) {
            updateUserData(request, response);
        }
        if ("updatePwd".equals(uris[uris.length - 1])) {
            updateUserPwd(request, response);
        }
        if ("logoutClient".equals(uris[uris.length - 1])) {
            logoutClient(request, response);
        }
        if ("headImgUpload".equals(uris[uris.length - 1])) {
            headImgUpload(request, response);
        }
     }

    /**
     * 将图片上传到服务器，然后返回图片地址
     * @param request
     * @param response
     * @throws IOException
     */
    private void headImgUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = FileUploadUtils.parseRequest(request);
        String file = (String) map.get("file");
        file = "http://" + request.getServerName() + ":" + request.getServerPort() + file;
        HttpUtils.responseWriterTrue(file, response);
    }

    /**
     * 用户退出登录
     * 将session清除即可
     * @param request
     * @param response
     */
    private void logoutClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 修改用户密码
     * 1.将json字符串解析成userpwd对象
     * 2.用该对象去更新数据库
     * 3.返回结果
     * post
     * 请求体：{"id":1000001,"oldPwd":"","newPwd":"","confirmPwd":""}
     * 响应体：{"code": 0}
     * @param request
     * @param response
     */
    private void updateUserPwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        UserPwd userPwd = gson.fromJson(requestBody, UserPwd.class);

        int result = userService.updateUserPwd(userPwd);
        if (result == 1) {
            //旧密码错误
            String message = "旧密码错误！";
            HttpUtils.responseWriterFalse(message, response);
        }
        if (result == 0) {
            HttpUtils.responseWriterTrue(null, response);
        }
    }

    /**
     * 1.将json字符串解析成user对象
     * 2.用该对象去数据库更新其对应的数据
     * 3.将结果返回
     * post
     * 请求体：{"id":"476","nickname":"啥玩意","recipient":"严汉老师","address":"富士达富士达","phone":"18811111111"}
     * 响应体：{"code":0}
     * @param request
     * @param response
     */
    private void updateUserData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        User user = gson.fromJson(requestBody, User.class);

        int result = userService.updateUserDate(user);
        if (result == 1) {
            String message = "该昵称被占用！";
            HttpUtils.responseWriterFalse(message, response);
        }
        if (result == 0) {
            HttpUtils.responseWriterTrue(null, response);
        }
    }

    /**
     * 1.将json字符串解析成User对象
     * 2.将该对象添加到数据库
     * 3.将结果返回
     * post
     * 请求体：{
     * 	"email": "123454@qq.com",
     * 	"nickname": "user123454",
     * 	"pwd": "User@123454",
     * 	"recipient": "严汉老师",
     * 	"address": "湖北省武汉市洪山区花山街道软件新城9号楼",
     * 	"phone": "14323333333"
     * }
     * 响应体：{"code":0,"data":{"code":0,"name":"user123454","token":"user123454"}}
     * @param request
     * @param response
     */
    private void signupUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        User user = gson.fromJson(requestBody, User.class);
        if (StringUtils.isEmpty(user.getImg())) {
            String message = "请选择头像！";
            HttpUtils.responseWriterFalse(message, response);
            return;
        }
        int result = userService.signupUser(user);
        if (result == 1) {
            String message = "该邮箱已被占用！";
            HttpUtils.responseWriterFalse(message, response);
        }
        if (result == 2) {
            String message = "该昵称已被占用！";
            HttpUtils.responseWriterFalse(message, response);
        }
        if (result == 0) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            //cookie.setMaxAge(0);
            response.addCookie(cookie);
            Map<String, Object> map = new HashMap<>();
            map.put("token", user.getNickname());
            map.put("name", user.getNickname());
            HttpUtils.responseWriterTrue(map, response);
        }
    }

    /**
     * 1.将json字符串解析成User对象
     * 2.去数据库查找与该对象相同的对象，并将之返回
     * 3.将结果返回
     * post
     * 请求体：{"email":"admin","pwd":"admin"}
     * 响应体：{"code":0,"data":{"code":0,"name":"admin","token":"admin"}}
     * @param request
     * @param response
     */
    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        User user = gson.fromJson(requestBody, User.class);
        User result = userService.loginUser(user);
        if (result == null) {
            String message = "用户名或者密码填写错误！";
            HttpUtils.responseWriterFalse(message, response);
        } else {
            HttpSession session = request.getSession();
            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            session.setAttribute("user", user);
            //cookie.setMaxAge(0);
            response.addCookie(cookie);
            Map<String, Object> map = new HashMap<>();
            map.put("name", result.getNickname());
            map.put("token", result.getNickname());
            HttpUtils.responseWriterTrue(map, response);
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
        if ("getUserInfo".equals(uris[uris.length - 1])) {
            getUserInfo(request, response);
        }
    }

    /**
     * 1.从数据库获取指定用户的详细信息
     * 2.将结果返回
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": {
     * 		"code": 0,
     * 		"id": "476",
     * 		"email": "1954047253@qq.com",
     * 		"nickname": "NoUtopin",
     * 		"recipient": "严汉老师",
     * 		"address": "富士达富士达",
     * 		"phone": "18811111111"
     *        }
     * }
     * @param request
     * @param response
     */
    private void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("token");
        User user = userService.getUserInfo(userName);
        HttpUtils.responseWriterTrue(user, response);
    }

}
