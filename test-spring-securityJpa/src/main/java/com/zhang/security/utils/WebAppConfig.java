package com.zhang.security.utils;


import com.zhang.security.anotation.Description;

public class WebAppConfig {
	
	@Description(value = "Get请求")
	public static final String REQUEST_METHOD_GET        = "GET";
	
	@Description(value = "POST请求")
	public static final String REQUEST_METHOD_POST       = "POST";
	
	@Description(value = "请求出错状态码")
	public static final String    ACTION_ERROR_CODE      = "500";
	
	@Description(value = "请求成功状态码")
	public static final String    ACTION_SUCCESS_CODE    = "200";
	
	@Description(value = "默认编码")
    public static final String ENCODING_UTF8            = "UTF-8";
    
	@Description(value = "GBK编码")
	public static final String ENCODING_GBK 			= "GBK";
	
	@Description("链接超时时间")
	public static final int CONNECTTIMEOUT = 15000;
	
	@Description("读取超时时间")
	public static final int READTIMEOUT = 15000;
	
	@Description(value = "数据操作影响行数")
    public static final int	ZERO	= 0;
    
    @Description("文件分隔符")
    public static String FILE_SEPARATOR = System.getProperty("file.separator");
    
    public static String OS_TYPE(){
    	return System.getProperty("file.separator").equals("\\") ? "windows" : "other";  //操作系统类型
    }
}
