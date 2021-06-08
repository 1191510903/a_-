package com.zhang.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhang.security.entity.SecurityCode;
import com.zhang.security.utils.ResponseUtil;
import com.zhang.security.utils.Result;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("=======================LoginFailureHandler===================");
        ResponseUtil.formatHeader(httpServletResponse);
        Result result = new Result();
        if(LockedException.class.isInstance(e)){
            result.setRestCode(SecurityCode.LOGIN_CLOCK_CODE);
        }else{
            result.setRestCode(SecurityCode.LOGIN_ERROR_CODE);
        }
        httpServletResponse.getWriter().print(JSONObject.toJSONString(result));
    }
}
