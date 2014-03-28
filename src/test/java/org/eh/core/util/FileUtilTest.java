package org.eh.core.util;

import org.eh.core.common.Constants;
import org.junit.Test;

/**
 *
 * @author guojing
 * @date 2014-3-4
 */
public class FileUtilTest {

	@Test
	public void TestRead() {
		String path = Constants.CLASS_PATH
				+ "org/eh/web/view/test/myinfo.page";
		String content = IOUtil.readFile(path);
		System.out.println(content);
	}

}
