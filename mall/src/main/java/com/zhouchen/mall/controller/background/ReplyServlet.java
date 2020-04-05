package com.zhouchen.mall.controller.background;

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

@WebServlet("/api/admin/reply/*")
public class ReplyServlet extends HttpServlet {
    static final int REPLYSTATE = 1;
    static final int NOREPLYSTATE = 0;
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
        //返回请求行中的资源名路径
        String requestURI = request.getRequestURI();
        String [] uris = requestURI.split("/");
        if ("reply".equals(uris[uris.length - 1])) {
            reply(request,response);
        }
    }

    /**
     * 1.将json字符串解析成question对象
     * 2.用该question对象更新数据库对应的数据
     * 3.返回结果
     * post
     * 请求体;{"id":312,"content":"你收啊好"}
     * 响应体;{"code": 0}
     * @param request
     * @param response
     */
    private void reply(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = HttpUtils.getRequestBody(request);
        Question question = gson.fromJson(body, Question.class);
        //进入数据库更改相应的数据
        replyService.reply(question);

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
        if ("noReplyMsg".equals(uris[uris.length - 1])) {
            noReplyMsg(request, response);
        }
        if ("repliedMsg".equals(uris[uris.length - 1])) {
            repliedMsg(request, response);
        }
    }

    /**
     * 1.去数据库获取已经回复的留言
     * 2.返回结果
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": [{
     * 		"id": 297,
     * 		"userId": 1,
     * 		"goodsId": 475,
     * 		"content": "这个衣服怎么样",
     * 		"replyContent": "很不错的",
     * 		"state": 0,
     * 		"createtime": "2019-10-12 11:22:18",
     * 		"goods": {
     * 			"name": "半身裙"
     *                },
     * 		"user": {
     * 			"name": "admin"
     *        }* 	}]
     * }
     * @param request
     * @param response
     */
    private void repliedMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Question> questions = replyService.getReplyMsg(REPLYSTATE);
        HttpUtils.responseWriterTrue(questions, response);
    }

    /**
     * 1.去数据库获取未回复的留言
     * 2.返回结果
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": [{
     * 		"id": 297,
     * 		"userId": 1,
     * 		"goodsId": 475,
     * 		"content": "这个衣服怎么样",
     * 		"replyContent": "很不错的",
     * 		"state": 0,
     * 		"createtime": "2019-10-12 11:22:18",
     * 		"goods": {
     * 			"name": "半身裙"
     *                },
     * 		"user": {
     * 			"name": "admin"
     *        }* 	}]
     * }
     * @param request
     * @param response
     */
    private void noReplyMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Question> questions = replyService.getReplyMsg(NOREPLYSTATE);
        HttpUtils.responseWriterTrue(questions, response);
    }
}
