package com.icourse.controller;

import com.icourse.common.Result;
import com.icourse.pojo.NewsType;
import com.icourse.pojo.vo.HeadlineDetailVo;
import com.icourse.pojo.vo.HeadlineQueryVo;
import com.icourse.service.NewsHeadlineService;
import com.icourse.service.NewsTypeService;
import com.icourse.service.impl.NewsHeadlineServiceImpl;
import com.icourse.service.impl.NewsTypeServiceImpl;
import com.icourse.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
门户 控制器
那些不需要登录,不需要做增删改的门户页的请求都放在这里
*/
@WebServlet("/portal/*")
public class PortalController extends BaseController{
    private NewsTypeService typeService =new NewsTypeServiceImpl();
    private NewsHeadlineService headlineService =new NewsHeadlineServiceImpl();

    protected void showHeadlineDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收要查询头条的hid
        int hid = Integer.parseInt(req.getParameter("hid"));
        // 调用服务层完成查询处理
        HeadlineDetailVo headlineDetailVo =headlineService.findHeadlineDetail(hid);
        // 将查到的信息响应给客户端
        Map data =new HashMap();
        data.put("headline",headlineDetailVo);
        WebUtil.writeJson(resp, Result.ok(data));
    }

    protected void findNewsPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的参数
        HeadlineQueryVo headlineQueryVo = WebUtil.readJson(req, HeadlineQueryVo.class);


        // 将参数传递给服务层 进行分页查询
       Map pageInfo =headlineService.findPage(headlineQueryVo);
       Map data =new HashMap();
       data.put("pageInfo",pageInfo);

        // 将分页查询的结果转换成json响应给客户端
        WebUtil.writeJson(resp,Result.ok(data));
    }
    protected void findAllTypes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 查询所有的新闻类型,装入Result响应给客户端
        List<NewsType> newsTypeList= typeService.findAll();


        WebUtil.writeJson(resp,Result.ok(newsTypeList));
    }
}
