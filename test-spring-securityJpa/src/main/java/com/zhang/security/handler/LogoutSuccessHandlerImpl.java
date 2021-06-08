package com.zhang.security.handler;


import com.alibaba.fastjson.JSONObject;
import com.zhang.security.entity.SecurityCode;
import com.zhang.security.utils.ResponseUtil;
import com.zhang.security.utils.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseUtil.formatHeader(response);
        Result result = new Result();
        result.setRestCode(SecurityCode.LOGIN_CODE);
        response.getWriter().print(JSONObject.toJSONString(result));}
}
