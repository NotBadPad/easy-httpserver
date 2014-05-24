package org.eh.core.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author guojing
 * @date 2014-3-3
 */
public class Constants {

	/* 配置信息 */
	public static String CLASS_PATH = "";//classes文件夹路径
	public static String VIEW_BASE_PATH = ""; // 路径
	public static String STATIC_RESOURCE_PATH = ""; // 静态文件路径
	public static List<String> STATIC_SUFFIXS = new ArrayList<String>(Arrays.asList(".css", ".js",
			".jpg", ".png", ".gif", ".html")); // 静态文件后缀
	
	/* session */
	public static Integer SESSION_TIMEOUT = 10; //session
	
	public static Map<String, String> UrlClassMap = new HashMap<String, String>(); // url与class映射
}
