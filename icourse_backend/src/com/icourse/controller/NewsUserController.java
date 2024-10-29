package com.icourse.controller;

import com.icourse.common.Result;
import com.icourse.common.ResultCodeEnum;
import com.icourse.pojo.NewsUser;
import com.icourse.service.NewsUserService;
import com.icourse.service.impl.NewsUserServiceImpl;
import com.icourse.util.JwtHelper;
import com.icourse.util.MD5Util;
import com.icourse.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class NewsUserController extends BaseController{


    private NewsUserService userService =new NewsUserServiceImpl();

    protected void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("token");

        Result result= Result.build(null,ResultCodeEnum.NOTLOGIN);
        if(null != token){
            if (!JwtHelper.isExpiration(token)) {
                result= Result.ok(null);
            }
        }
        WebUtil.writeJson(resp,result);
    }

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收JSON信息
        NewsUser registUser = WebUtil.readJson(req, NewsUser.class);

        // 调用服务层将用户信息存入数据
        Integer rows =userService.registUser(registUser);

        // 根据存入是否成功处理响应值
        Result result =Result.ok(null);
        if(rows == 0){
            result=Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        WebUtil.writeJson(resp,result);

    }

    protected void checkUserName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取账号
        String username = req.getParameter("username");

        // 根据用户名查询用户信息  找到了 返回505  找不到 200
        NewsUser newsUser = userService.findByUsername(username);
        Result result =Result.ok(null);
        if(null != newsUser){
            result=Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        WebUtil.writeJson(resp,result);
    }

    protected void getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求中的token
        String token = req.getHeader("token");


        Result result = Result.build(null,ResultCodeEnum.NOTLOGIN);
        if(null != token && (!"".equals(token))){
            if (!JwtHelper.isExpiration(token)) {
                Integer userId = JwtHelper.getUserId(token).intValue();
                NewsUser newsUser =userService.findByUid(userId);
                if(null != newsUser){
                    //通过校验 查询用户信息放入Result
                    Map data =new HashMap();
                    newsUser.setUserPwd("");
                    data.put("loginUser",newsUser);

                    result = Result.ok(data);
                }
            }
        }

        WebUtil.writeJson(resp,result);

    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收用户名和密码
        /*{
            "username":"zhangsan", //用户名
            "userPwd":"123456"     //明文密码
        }*/
        NewsUser paramUser = WebUtil.readJson(req, NewsUser.class);

        // 调用服务层方法 实现登录
        NewsUser loginUser = userService.findByUsername(paramUser.getUsername());
        Result result = null;
        if(null != loginUser){
            if (MD5Util.encrypt(paramUser.getUserPwd()).equalsIgnoreCase(loginUser.getUserPwd())) {
                Map data =new HashMap();
                data.put("token",JwtHelper.createToken(loginUser.getUid().longValue()));
                result=Result.ok(data);
            }else{
                result=Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
            }
        }else {
            result=Result.build(null, ResultCodeEnum.USERNAME_ERROR);

        }

        // 向客户端响应登录验证信息
        WebUtil.writeJson(resp,result);
    }
}
