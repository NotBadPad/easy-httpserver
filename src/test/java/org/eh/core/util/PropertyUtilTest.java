package org.eh.core.util;

import org.junit.Test;

/**
 *
 * @author guojing
 * @date 2014-3-4
 */
public class PropertyUtilTest {

	@Test
	public void testResd() {
		PropertyUtil.analysisProperties(this.getClass().getResource("/").getPath()
				+ "web.properties");
	}

	@Test
	public void testReflect() {
	}
}
