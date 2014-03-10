package org.eh.velocity;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;

/**
 *
 * @author guojing
 * @date 2014-3-10
 */
public class VelocityTest {

	@Test
	public void testVelocity() {
		Properties p =new Properties();
		p.setProperty("resource.loader", "class");
		p.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		
		Velocity.init(p);
		VelocityContext vc = new VelocityContext();
		vc.put("name", "guojing");
		StringWriter w = new StringWriter();
		Velocity.mergeTemplate("org/eh/velocity/test.vm", "utf-8", vc, w);
		System.out.println(w);
	}
}
