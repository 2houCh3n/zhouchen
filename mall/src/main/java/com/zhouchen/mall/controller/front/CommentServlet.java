package com.zhouchen.mall.controller.front;

import com.google.gson.Gson;
import com.zhouchen.mall.bean.comment.Comment;
import com.zhouchen.mall.service.front.CommentService;
import com.zhouchen.mall.service.front.impl.CommentServiceImpl;
import com.zhouchen.mall.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/mall/comment/*")
public class CommentServlet extends HttpServlet {
    CommentService commentService = new CommentServiceImpl();
    Gson gson  = new Gson();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String [] uris = requestURI.split("/");
        if ("sendComment".equals(uris[uris.length - 1])) {
            sendComment(request, response);
        }
    }

    /**
     * 对指定商品发表评论
     * post
     * 请求体：{"token":"admin","orderId":1780,"goodsId":482,"goodsDetailId":1248,"content":"很好看","score":80}
     * @param request
     * @param response
     */
    private void sendComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.getRequestBody(request);
        Comment comment = gson.fromJson(requestBody, Comment.class);
        commentService.sendComment(comment);
        HttpUtils.responseWriterTrue(null, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String [] uris = requestURI.split("/");
        if ("getGoodComment".equals(uris[uris.length - 1])) {
            getGoodComments(request, response);
        }
    }

    /**
     * 1.从数据库中获取指定商品的所有评论，以list形式返回
     * 2.将结果返回
     * get
     * 响应体：
     * {
     * 	"code": 0,
     * 	"data": {
     * 		"commentList": [{
     * 			"user": {
     * 				"nickname": "admin"
     *           },
     * 			"score": 100.0,
     * 			"id": 107,
     * 			"specName": "x",
     * 			"comment": "噜啦噜啦啦啦啦",
     * 			"time": "2020-04-02 12:41:10",
     * 			"userId": 1
     * 		 }],
     * 		"rate": 50.0
     *    }
     * }
     * @param request
     * @param response
     */
    private void getGoodComments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int goodId = Integer.parseInt(request.getParameter("goodsId"));
        List<Comment> comments = commentService.getGoodComments(goodId);
        Map<String, Object> map = new HashMap<>();
        map.put("commentList", comments);
        double score = 0;
        for (Comment comment : comments) {
            score += comment.getScore();
        }
        if (comments.size() == 0) {
            map.put("rate", 0);
        } else {
            double rate = score / comments.size();
            map.put("rate", rate);
        }
        HttpUtils.responseWriterTrue(map, response);
    }
}
