package org.eh.core.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Velocity解析类
 * @author guojing
 * @date 2014-3-10
 */
public class VelocityUtil {

	/**
	 * 渲染Velocity模板
	 * @param path
	 * @param map
	 */
	public static String mergeTemplate(String path, Map<String, Object> map) throws IOException {
		VelocityContext vc = new VelocityContext();
		if (null != map) {
			for (String key : map.keySet()) {
				vc.put(key, map.get(key));
			}
		}
		StringWriter w = new StringWriter();
		Velocity.mergeTemplate(path, "utf-8", vc, w);
		String content = w.toString();
		w.close();
		return content;
	}
}
