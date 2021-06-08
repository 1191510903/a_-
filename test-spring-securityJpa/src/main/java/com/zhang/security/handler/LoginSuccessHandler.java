package com.zhang.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhang.security.entity.SecurityCode;
import com.zhang.security.entity.User;
import com.zhang.security.entity.UserDetail;
import com.zhang.security.filter.AuthenticationTokenProcessingFilter;
import com.zhang.security.utils.ResponseUtil;
import com.zhang.security.utils.TokenUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



/**
 * 用户登录成功验证
 */

//public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("=============LoginSuccessHandler===============");
        ResponseUtil.formatHeader(response);
        response.setCharacterEncoding("UTF-8");
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("restCode", SecurityCode.LOGIN_SUCCESS_CODE);
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetail){
            System.out.println("=====================UserDetails==================");
        }
        //认证TOKEN
        res.put("token", TokenUtils.createToken((UserDetails)authentication.getPrincipal()));
        //登录用户信息
        User user = (User)authentication.getPrincipal();

        if (AuthenticationTokenProcessingFilter.userInfo.containsKey(user.getLoginId())) {
            AuthenticationTokenProcessingFilter.userInfo.remove(user.getLoginId());
        }
        res.put("pageSize", "30");
        String option = "30,50,100";

        res.put("pageOption", option.split(","));
        JSONObject json = new JSONObject(res);
//        sysLogService.addOperateLog("登录成功",user,request);
        response.getWriter().print(json.toJSONString());
    }


    //    @Autowired
//    private SysLogService sysLogService;

//
//    Logger log = LoggerFactory.getLogger(AuthenticationTokenProcessingFilter.class);
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response, Authentication authentication)
//            throws ServletException, IOException {
//        ResponseUtil.formatHeader(response);
//        response.setCharacterEncoding("UTF-8");
//        Map<String,Object> res = new HashMap<String,Object>();
//        res.put("restCode", SecurityCode.LOGIN_SUCCESS_CODE);
//        //认证TOKEN
//        res.put("token", TokenUtils.createToken((UserDetails)authentication.getPrincipal()));
//        //登录用户信息
//        User user = (User)authentication.getPrincipal();
//        AuthenticationTokenProcessingFilter.userInfo.put(user.getLoginId(), user);
//        res.put("pageSize", "30");
//        String option = "30,50,100";
//
//        res.put("pageOption", option.split(","));
//        JSONObject json = new JSONObject(res);
////        sysLogService.addOperateLog("登录成功",user,request);
//        response.getWriter().print(json.toJSONString());
//    }
}
