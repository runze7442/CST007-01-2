package com.icourse.filters;


import com.icourse.common.Result;
import com.icourse.common.ResultCodeEnum;
import com.icourse.util.JwtHelper;
import com.icourse.util.WebUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/headline/*")
public class LoginFilter  implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        HttpServletResponse response =(HttpServletResponse) servletResponse;
        String token = request.getHeader("token");

        boolean flag = null != token && (!JwtHelper.isExpiration(token));

        if(flag){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            WebUtil.writeJson(response, Result.build(null, ResultCodeEnum.NOTLOGIN));
        }



    }
}
