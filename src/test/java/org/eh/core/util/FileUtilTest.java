package org.eh.core.util;

import org.junit.Test;

/**
 *
 * @author guojing
 * @date 2014-3-4
 */
public class FileUtilTest {

	@Test
	public void TestRead() {
		String path = this.getClass().getResource("/").getPath()
				+ "org/eh/web/view/test/myinfo.page";
		String content = IOUtil.readFile(path);
		System.out.println(content);
	}

}
