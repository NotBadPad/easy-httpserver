package org.eh.core.controller;

import java.util.Map;

import org.eh.core.annotation.RequestMapping;
import org.eh.core.web.controller.Controller;

/**
 *
 * @author guojing
 * @date 2014-3-5
 */
@org.eh.core.annotation.Controller(name = "test1", url = "/test/show")
public class Test1Controller implements Controller {

	@RequestMapping
	public String add(Map<String, Object> map) {
		System.out.println("aaa");
		return null;
	}
}
