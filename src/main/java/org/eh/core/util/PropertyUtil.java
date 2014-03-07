package org.eh.core.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author guojing
 * @date 2014-3-4
 */
public class PropertyUtil {
	private static final Log log = LogFactory.getLog(PropertyUtil.class);
	public static Map<String, String> analysisProperties(String path) {
		Map<String, String> map = new HashMap<String, String>();
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			props.load(in);
			for (String key : props.stringPropertyNames()) {
				map.put(key, props.get(key).toString());
			}
		} catch (Exception e) {
			log.error("配置文件解析错误：", e);
		}
		return map;
	}
}
