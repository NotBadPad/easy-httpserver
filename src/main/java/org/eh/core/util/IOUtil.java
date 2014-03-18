package org.eh.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * IO、文件处理
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

	public static boolean isExist(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static String readFile(String path) {
		if (!isExist(path)) {
			return "";
		}
		FileInputStream fis = null;
		StringBuilder sb = new StringBuilder();
		try {
			fis = new FileInputStream(path);
			FileChannel c = fis.getChannel();
			ByteBuffer bc = ByteBuffer.allocate(1024);
			int i = c.read(bc);
			while (i != -1) {
				sb.append(new String(bc.array()));
				bc.clear();
				i = c.read(bc);
			}
			c.close();
			fis.close();
		} catch (Exception e) {
			log.error("读取模板文件失败：", e);
		}
		return sb.toString();
	}

	public static byte[] readFileByBytes(String path) {
		if (!isExist(path)) {
			return "".getBytes();
		}
		byte[] bytes = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			FileChannel c = fis.getChannel();
			ByteBuffer bc = ByteBuffer.allocate((int) c.size());
			int i = c.read(bc);
			if (i != -1) {
				bytes = bc.array();
			}
			c.close();
			fis.close();
		} catch (Exception e) {
			log.error("读取资源文件失败：", e);
		}
		return bytes;
	}
}
