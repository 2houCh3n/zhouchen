package com.zhouchen.mall.controller.background;

import com.google.gson.Gson;
import com.zhouchen.mall.bean.Result;
import com.zhouchen.mall.bean.admin.Admin;
import com.zhouchen.mall.bean.admin.AdminPwd;
import com.zhouchen.mall.service.background.AdminService;
import com.zhouchen.mall.service.background.impl.AdminServiceImpl;
import com.zhouchen.mall.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/admin/admin/*")
public class AdminServlet extends HttpServlet {

    AdminService adminService = new AdminServiceImpl();
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
        if ("login".equals(uris[uris.length - 1])) {
            loginAdmin(request, response);
        }
        if ("addAdmin".equals(uris[uris.length - 1])) {
            addAdmin(request, response);
        }
        if ("updateAdmin".equals(uris[uris.length - 1])) {
            updateAdmin(request, response);
        }
        if ("getSearchAdmin".equals(uris[uris.length - 1])) {
            getSearchAdmins(request, response);
        }
        if ("changePwd".equals(uris[uris.length - 1])) {
            changePwd(request, response);
        }
        if ("logoutAdmin".equals(uris[uris.length - 1])) {
            logoutAdmin(request, response);
        }
    }

    /**
     * 推出登录，清楚session
     * @param request
     * @param response
     */
    private void logoutAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("admin", null);
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 1.将json字符串解析成admin对象
     * 2.然后用该数据更新数据库
     * 3.返回结果
     * post
     * 请求体：{adminToken: "root", oldPwd: "123131421", newPwd: "4321423", confirmPwd: "431423"}
     * 响应体：{"code":10000,"message":"旧密码错误！"}
     * @param request
     * @param response
     */
    private void changePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = HttpUtils.getRequestBody(request);
        AdminPwd adminPwd = gson.fromJson(body, AdminPwd.class);

        int result = adminService.changePwd(adminPwd);
        Result res = new Result();
        if (result == 0) {
            res.setCode(0);
        }
        if (result == 1) {
            res.setCode(10000);
            res.setMessage("旧密码错误！");
        }
        response.getWriter().println(gson.toJson(res));
    }

    /**
     * 1.将json字符串解析成admin对象
     * 2.根据该数据去数据库中查找该数据
     * 3.返回结果
     * post
     * 请求体：{nickname: "", email: "root"}
     * 响应体：{"code":0,"data":[{"id":100,"email":"root@qq.com","nickname":"root","pwd":"Root@100"}]}
     * @param request
     * @param response
     */
    private void getSearchAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);

        List<Admin> admins = adminService.getSearchAdmins(admin);
        HttpUtils.responseWriterTrue(admins, response);
    }

    /**
     * 1.将json字符串解析成admin对象
     * 2.然后用该数据更新数据库
     * 3.返回结果
     * post
     * 请求体;{id: 110, nickname: "qq12342", email: "12342@qq.com", pwd: "Qq@12342"}
     * 响应体：{"code":0}
     * @param request
     * @param response
     */
    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);

        int result = adminService.updateAdmin(admin);
        Result res = new Result();
        if (result == 0) {
            res.setCode(0);
        }
        if (result == 2) {
            res.setCode(10000);
            res.setMessage("该用户名不允许重复使用");
        }
        if (result == 1) {
            res.setCode(10000);
            res.setMessage("该账号不允许重复使用");
        }
        response.getWriter().println(gson.toJson(res));
    }

    /**
     * 1.将json字符串解析成admin对象
     * 2.然后将该数据添加到数据库
     * 3.返回结果
     * post
     * 请求体：{nickname: "qq12342", email: "12342@qq.com", pwd: "Qq@12342"}
     * 响应体：{"code":0}
     * @param request
     * @param response
     */
    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);

        int result = adminService.addAdmin(admin);
        Result res = new Result();
        if (result == 0) {
            res.setCode(0);
        }
        if (result == 1) {
            res.setCode(10000);
            res.setMessage("该账号不允许重复使用");
        }
        if (result == 2) {
            res.setCode(10000);
            res.setMessage("该用户名不允许重复使用");
        }
        response.getWriter().println(gson.toJson(res));
    }

    /**
     * 1.将json字符串解析成admin对象
     * 2.去数据库查询该对象
     * 3.返回结果
     * post
     * 请求体：{email: "root@qq.com", pwd: "Root@100"}
     * 响应体：{"code":0,"data":{"name":"root","token":"root"}}
     * @param request
     * @param response
     */
    private void loginAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);

        int result = adminService.loginAdmin(admin);

        Result res = new Result();
        if (result == 0) {
            res.setCode(0);
            Map<String, Object> map = new HashMap<>();
            map.put("token", admin.getNickname());
            map.put("name", admin.getNickname());
            map.put("level", admin.getLevel());
            res.setData(map);
            // 设置session
            HttpSession session = request.getSession();
            String sessionID = session.getId();
            Cookie cookie = new Cookie("JSESSIONID",sessionID);
            //cookie.setMaxAge(0);
            session.setAttribute("admin", admin);
            response.addCookie(cookie);
        } else if (result == 1) {
            res.setCode(10000);
            res.setMessage("该账号不存在");
        } else if (result == 2) {
            res.setCode(10000);
            res.setMessage("密码不正确！");
        }
        response.getWriter().println(gson.toJson(res));
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
        String[] uris = requestURI.split("/");
        if ("allAdmins".equals(uris[uris.length - 1])) {
            getAlldAmins(request, response);
        }
        if ("deleteAdmin".equals(uris[uris.length - 1])) {
            deleteAdmin(request, response);
        }
        if ("getAdminInfo".equals(uris[uris.length - 1])) {
            getAdminInfo(request, response);
        }
    }

    /**
     * 1.从请求头中提取出请求参数
     * 2.根据请求参数，去数据库中获取数据
     * 3.返回结果
     * get
     * 响应体：{"code":0,"data":{"id":110,"email":"12342@qq.com","nickname":"qq12342","pwd":"Qq@12342"}}
     * @param request
     * @param response
     */
    private void getAdminInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Admin admin = adminService.getAdminInfo(id);
        HttpUtils.responseWriterTrue(admin, response);
    }

    /**
     * 1.从请求头中提取出请求参数
     * 2.根据请求参数，去数据库中删除数据
     * 3.返回结果
     * get
     * 响应体：{"code":0}
     * @param request
     * @param response
     */
    private void deleteAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        adminService.deleteAdmin(id);
        HttpUtils.responseWriterTrue(null, response);
    }

    /**
     * 1.从数据库读取所有的管理员信息，用List封装
     * 2.然后将之写入响应体
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": [{
     * 		"id": 100,
     * 		"email": "root@qq.com",
     * 		"nickname": "root",
     * 		"pwd": "Root@100"
     *        }]
     * }
     * @param request
     * @param response
     */
    private void getAlldAmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Admin> admins = adminService.getAllAdmins();
        HttpUtils.responseWriterTrue(admins, response);
    }
}
