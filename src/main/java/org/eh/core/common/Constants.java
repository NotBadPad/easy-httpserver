package org.eh.core.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
