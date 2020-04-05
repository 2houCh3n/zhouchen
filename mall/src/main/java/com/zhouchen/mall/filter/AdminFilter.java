package com.zhouchen.mall.filter;

import com.google.gson.Gson;
import com.zhouchen.mall.bean.Result;
import com.zhouchen.mall.bean.admin.Admin;
import com.zhouchen.mall.util.HttpUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/api/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        //前后端交互，涉及到跨域问题，需要添加如下代码，以给前端的每次请求返回一个授权状态
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //chain.doFilter(request, response);

        if(!method.equalsIgnoreCase("OPTIONS")){
            //不是options请求，需要判断是否需要权限
            if(auth(requestURI)){
                //是否需要权限
                // /api/admin/admin/allAdmins
                Object email = request.getSession().getAttribute("admin");
                if(email == null){
                    //表示当前请求需要权限，但是此时session中没有东西
                    HttpUtils.responseWriterFalse("没有权限，请先登录！", response);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * 是否需要权限
     * @param requestURI
     * @return
     */
    private boolean auth(String requestURI) {
        if("/api/admin/admin/login".equalsIgnoreCase(requestURI)){
            return false;
        }
        if("/api/admin/admin/logoutAdmin".equalsIgnoreCase(requestURI)){
            return false;
        }
        return true;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }
}
