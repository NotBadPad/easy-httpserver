package org.eh.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author guojing
 * @date 2014-3-4
 */
public class IOUtil {
	private static final Log log = LogFactory.getLog(IOUtil.class);
	public static String getRequestContent(InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder sb = new StringBuilder();
		try {
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
		} catch (IOException e) {
			log.error("读取请求内容错误：", e);
		}
		return sb.toString();
	}
}
