package com.zhouchen.mall.controller.front;

import com.google.gson.Gson;
import com.zhouchen.mall.bean.reply.Question;
import com.zhouchen.mall.service.background.ReplyService;
import com.zhouchen.mall.service.background.impl.ReplyServiceImpl;
import com.zhouchen.mall.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/mall/reply/*")
public class ReplyServlet extends HttpServlet {
    ReplyService replyService = new ReplyServiceImpl();
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
        if ("askGoodMsg".equals(uris[uris.length - 1])) {
            askGoodMsg(request, response);
        }
    }

    /**
     * 1.将json字符串解析成question对象
     * 2.将该question对象添加到数据库中
     * 3.返回结果
     * post
     * 请求体：{"token":"NoUtopin","msg":"穿上这双鞋后，詹姆斯是我对手么","goodsId":"534"}
     * 响应体：{"code":0}
     * @param request
     * @param response
     */
    private void askGoodMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Question question = gson.fromJson(requestBody, Question.class);
        replyService.askGoodMsg(question);
        HttpUtils.responseWriterTrue(null, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String [] uris = requestURI.split("/");
        if ("getGoodMsg".equals(uris[uris.length - 1])) {
            getGoodMsgs(request, response);
        }
    }

    /**
     * 1.从数据库获取指定商品对应的所有的问答信息，以lsit形式返回
     * 2.将结果返回
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": [{
     * 		"id": 297,
     * 		"content": "这个衣服怎么样",
     * 		"asker": "admin",
     * 		"time": "2019-10-12 11:22:18",
     * 		"reply": {
     * 			"content": "很不错的",
     * 			"time": "2019-10-12 11:22:55"
     *        }
     *    }]
     * }
     * @param request
     * @param response
     */
    private void getGoodMsgs(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int goodId = Integer.parseInt(request.getParameter("id"));
        List<Question> questions = replyService.getGoodMsgs(goodId);
        HttpUtils.responseWriterTrue(questions, response);
    }
}
