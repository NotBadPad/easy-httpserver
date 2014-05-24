package org.eh.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller注解
 * @author guojing
 * @date 2014-3-5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Controller {

	/**
	 * controller名，暂时无用
	 */
	public String name();

	/**
	 * 对应url请求路径，如htp://127.0.0.1/test/list.do 则对应 controller为：/test/，对应方法为：list
	 */
	public String url();
}
