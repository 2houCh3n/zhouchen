package com.zhouchen.mall.util;

import com.google.gson.Gson;
import com.zhouchen.mall.bean.Result;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User：zhouchen
 * Time: 2020/3/30  9:17
 * Description:
 */
public class HttpUtils {
    static Gson gson = new Gson();

    /**
     * 将请求体中的数据提取成字符串
     * @param request
     * @return
     * 返回字符串结果
     */
    public static String getRequestBody(HttpServletRequest request){
        ByteArrayOutputStream outputStream = null;
        String s = null;
        try {
            ServletInputStream inputStream =request.getInputStream();
            outputStream = new ByteArrayOutputStream();
            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            s = outputStream.toString("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 正常请求返回后，将结果封装到json对象中，写入响应体中
     * @param data
     * @param response
     */
    public static void responseWriterTrue(Object data, HttpServletResponse response) throws IOException {
        Result res = new Result();
        res.setCode(0);
        res.setData(data);
        response.getWriter().println(gson.toJson(res));
    }

    /**
     * 当请求结果有异常时，就将结果封装到json对象中，写入响应体
     * @param message
     * @param response
     * @throws IOException
     */
    public static void responseWriterFalse(String message, HttpServletResponse response) throws IOException {
        Result res = new Result();
        res.setCode(10000);
        res.setMessage(message);
        response.getWriter().println(gson.toJson(res));
    }

}
