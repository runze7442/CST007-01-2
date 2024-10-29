package com.icourse.controller;

import com.icourse.common.Result;
import com.icourse.pojo.NewsHeadline;
import com.icourse.service.NewsHeadlineService;
import com.icourse.service.impl.NewsHeadlineServiceImpl;
import com.icourse.util.JwtHelper;
import com.icourse.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/headline/*")
public class NewsHeadlineController extends BaseController{
    private NewsHeadlineService headlineService =new NewsHeadlineServiceImpl();

    protected void removeByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int hid = Integer.parseInt(req.getParameter("hid"));

        headlineService.removeByHid(hid);


        WebUtil.writeJson(resp,Result.ok(null));
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsHeadline newsHeadline = WebUtil.readJson(req, NewsHeadline.class);
        headlineService.update(newsHeadline);

        WebUtil.writeJson(resp,Result.ok(null));

    }

    protected void findHeadlineByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer hid = Integer.parseInt(req.getParameter("hid"));
        NewsHeadline headline =headlineService.findByHid(hid);

        Map data =new HashMap();
        data.put("headline",headline);
        WebUtil.writeJson(resp,Result.ok(data));

    }

    protected void publish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收参数
        String token = req.getHeader("token");
        Long userId = JwtHelper.getUserId(token);


        NewsHeadline newsHeadline = WebUtil.readJson(req, NewsHeadline.class);
        newsHeadline.setPublisher(userId.intValue());


        // 将信息存入数据库
        headlineService.addNewsHeadline(newsHeadline);

        WebUtil.writeJson(resp, Result.ok(null));

    }
}
