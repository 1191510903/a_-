package com.zhang.security.utils;

import javax.servlet.http.HttpServletResponse;


/**
 *  返回头参数设置
 */
public class ResponseUtil {

    public static void formatHeader(HttpServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS");
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.addHeader("Access-Control-Allow-Headers", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,X-Auth-Token");
    }
    
}
