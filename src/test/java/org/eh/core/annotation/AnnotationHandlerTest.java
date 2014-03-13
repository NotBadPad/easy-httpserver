package org.eh.core.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Test;

/**
 *
 * @author guojing
 * @date 2014-3-13
 */
public class AnnotationHandlerTest {

	@Test
	@SuppressWarnings("rawtypes")
	public void testMethods() {
		try {
			Class c = Class.forName("org.eh.core.controller.Test1Controller");
			Method[] methods = c.getMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(RequestMapping.class)) {
					System.out.println(method.getName());
					org.eh.core.web.controller.Controller controller = (org.eh.core.web.controller.Controller) c
							.newInstance();
					method.invoke(controller, new Object[] { null });
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMethod() {
		try {
			Class c = Class.forName("org.eh.core.controller.Test1Controller");
			Method methods = c.getMethod("add", Map.class);
			System.out.println(methods.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
