package org.eh.core.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author guojing
 * @date 2014-3-4
 */
public class PropertyUtil {

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
			e.printStackTrace();
		}
		return map;
	}
}
