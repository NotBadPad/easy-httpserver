package org.eh.core.util;

import java.util.UUID;

/**
 *
 * @author guojing
 * @date 2014-3-4
 */
public class StringUtil {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static String creatSession() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
