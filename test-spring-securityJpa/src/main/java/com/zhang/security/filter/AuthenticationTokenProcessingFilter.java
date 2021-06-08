package com.zhang.security.filter;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.zhang.security.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebFilter(urlPatterns = "/*", filterName = "authFilter")
public class AuthenticationTokenProcessingFilter implements Filter {
    public static String autoUser = "autoUser";

    public static Map<String, Object> userInfo = new HashMap<String, Object>();
    Logger log = LoggerFactory.getLogger(AuthenticationTokenProcessingFilter.class);
    private UserDetailsService fitechUserDetailService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = this.getAsHttpRequest(request);
        System.out.println("===============AuthenticationTokenProcessingFilter==============");
        String authToken = this.extractAuthTokenFromRequest(httpRequest);
        String userName = TokenUtils.getUserNameFromToken(authToken);
        String uri = httpRequest.getRequestURI();
        log.info("uri===============" + uri);
        if (userName != null && !uri.contains("/webSocketServer") && !uri.contains("/User/loginOut")) {
            //TODO 临时使用( 如果服务器缓存中有用户登录信息)
            UserDetails userDetails = null;

            if (!userInfo.containsKey(userName)) {
                //模拟登陆赋值用户名
                userInfo.put(userName, userName);
                userDetails = this.fitechUserDetailService.loadUserByUsername(userName);
                userInfo.put(userName, userDetails);
            } else {
                // 首次登陆保存的是用户基本信息
                if (uri.contains("/menu/userAllMenu")) {
                    userDetails = this.fitechUserDetailService.loadUserByUsername(userName);
                    userInfo.put(userName, userDetails);
                } else {
                    try {
                        userDetails = (UserDetails) userInfo.get(userName);
                    } catch (Exception e) {
                        userInfo.put(userName, userName);
                        userDetails = this.fitechUserDetailService.loadUserByUsername(userName);
                        userInfo.put(userName, userDetails);
                    }
                }
            }


            if (TokenUtils.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {

            }
        }
        chain.doFilter(request, response);
    }

    private HttpServletRequest getAsHttpRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }
        return (HttpServletRequest) request;
    }

    private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
        String authToken = httpRequest.getHeader("X-Auth-Token");
        if (authToken == null) {
            authToken = httpRequest.getParameter("token");
        }
        return authToken;
    }

    public void setFitechUserDetailService(
            UserDetailsService fitechUserDetailService) {
        this.fitechUserDetailService = fitechUserDetailService;
    }
}
