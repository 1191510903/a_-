package com.zhang.security.entity;

import com.zhang.security.anotation.Description;

public class SecurityCode {
	@Description("系统未登录")
	public static final String LOGIN_CODE			="000000";
	@Description("系统登录成功")
	public static final String LOGIN_SUCCESS_CODE	="100001";
	@Description("系统登录异常")
	public static final String LOGIN_ERROR_CODE		="900001";
	@Description("资源无权限")
	public static final String NO_AUTHORITY			="900002";
	@Description("用户被锁定或过期")
	public static final String LOGIN_CLOCK_CODE		="900003";
	@Description("单点登录认证异常")
	public static final String LOGIN_SSO_AUTH_ERROR		="900004";
	
	
}
