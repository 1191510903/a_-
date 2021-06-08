package com.zhang.security.filter;

import com.zhang.security.utils.DESUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // 用户名
    public static final String FORM_USERNAME_KEY = "username";
    // 密码
    public static final String FORM_PASSWORD_KEY = "password";

    public static final String FORM_USERWORD_KEY = "userwords";

    public  static final String DES_KEY = "123456";


    public LoginAuthenticationFilter() {
        super();
    }
    /**
     * 自定义登录验证
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("=================LoginAuthenticationFilter================================");

        // 前端DES加密
        String userWords = obtainUserWord(request);
        if(!StringUtils.isEmpty(userWords)){
            String[] uws = userWords.split("\\|");
            String username = uws[0];
            String password = uws[1];
            System.out.println("=================userWords======================"+userWords);

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,password);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }else{
            String username = obtainUsername(request);
            String password = obtainPassword(request);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }

    }

    // 前端DES加密后密文，需解析
    protected String obtainUserWord(HttpServletRequest request) {
        String userWord = request.getParameter(FORM_USERWORD_KEY)==null?"":request.getParameter(FORM_USERWORD_KEY).trim();
        System.out.println("request=================:"+userWord);
        return DESUtil.decryptToString(userWord, LoginAuthenticationFilter.DES_KEY);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(FORM_USERNAME_KEY)==null?"":request.getParameter(FORM_USERNAME_KEY).trim();
    }
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(FORM_PASSWORD_KEY)==null?"":request.getParameter(FORM_PASSWORD_KEY);
    }
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
