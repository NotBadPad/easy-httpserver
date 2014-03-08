package org.eh.core.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eh.core.util.PropertyUtil;

/**
 *
 * @author guojing
 * @date 2014-3-3
 */
public class Constants {
	private static final Log log = LogFactory.getLog(Constants.class);

	/* 配置信息 */
	public static String PACKAGE_PREFIX = "org.eh.web."; // 包前缀
	public static String VIEW_BASE_PATH = "org/eh/web/view/"; // view包路径

	public static Map<String, String> UrlClassMap = new HashMap<String, String>(); // url与class映射
	public static Map<String, String> OTHER_CONFIG_INFO = new HashMap<String, String>(); // 其他配置信息
	public static List<String> STATIC_SUFFIXS = new ArrayList<String>(Arrays.asList(".css", ".js",
			".jpg", ".png", ".gif")); // 静态文件后缀

	/* 常量值 */
	public static String PROPERTIES_NAME = "web.properties"; // 配置文件名
	public static String PROPERTIES_CONTROLLER_PACKAGE = "controller.package"; // controller配置文件中属性名
	public static String PROPERTIES_HPPTSERVER_PORT = "httpserver.port"; // 服务端口名

	public static void loadFromProp(String path) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = PropertyUtil.analysisProperties(path);
		} catch (Exception e) {
			log.error("配置文件不存在！", e);
		}

		for (String key : map.keySet()) {
			if (key.equals("PACKAGE_PREFIX")) {
				PACKAGE_PREFIX = map.get(key).toString();
			} else if (key.equals("VIEW_BASE_PATH")) {
				VIEW_BASE_PATH = map.get(key).toString();
			} else if (key.startsWith("url")) {
				UrlClassMap.put(key.replace("url", ""), map.get(key).toString());
			} else {
				OTHER_CONFIG_INFO.put(key, map.get(key).toString());
			}
		}
	}
}
