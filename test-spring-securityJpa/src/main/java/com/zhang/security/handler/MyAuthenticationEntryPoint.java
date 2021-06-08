package com.zhang.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhang.security.entity.SecurityCode;
import com.zhang.security.utils.ResponseUtil;
import com.zhang.security.utils.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 自定义登录切入点，未授权资源定向登录页面
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseUtil.formatHeader(response);
        Result result = new Result();
        result.setRestCode(SecurityCode.LOGIN_CODE);
        response.getWriter().print(JSONObject.toJSONString(result));
    }
}