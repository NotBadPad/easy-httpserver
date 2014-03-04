package org.eh.web.controller;

import java.util.HashMap;
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "guojing");
		map.put("msg", "Hello!");

		resultInfo.setResultMap(map);
		resultInfo.setView("test/myinfo");
		return resultInfo;
	}

}
