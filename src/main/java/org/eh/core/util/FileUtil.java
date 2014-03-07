package org.eh.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author guojing
 * @date 2014-3-3
 */
public class FileUtil {
	private static final Log log = LogFactory.getLog(FileUtil.class);
	
	public static boolean isExist(String path) {
		File file = new File(path);
		return file.exists();
	}

	@SuppressWarnings("resource")
	public static String readFile(String path) {
		System.out.println(path);
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
		System.out.println(path);
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
