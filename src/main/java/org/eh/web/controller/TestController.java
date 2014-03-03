package org.eh.web.controller;

import java.util.Map;

import org.eh.core.model.ResultInfo;
import org.eh.core.web.controller.Controller;

/**
 *
 * @author guojing
 * @date 2014-3-3
 */
public class TestController implements Controller {

	@SuppressWarnings("unchecked")
	public ResultInfo process(Map<String, Object> parms) {
		ResultInfo resultInfo = new ResultInfo();
		resultInfo.getResultMap().put("name", "guojing");
		resultInfo.getResultMap().put("msg", "Hello!");
		resultInfo.setView("test/myinfo");
		return resultInfo;
	}

}
