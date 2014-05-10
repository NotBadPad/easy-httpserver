package org.eh.core.web.controller;

import java.util.Map;

import org.eh.core.http.ApplicationContext;
import org.eh.core.http.HttpSession;
import org.eh.core.model.ResultInfo;

/**
 * 主页对应的contoller
 * @author guojing
 */
public class IndexController implements Controller{
	
	public ResultInfo process(Map<String, Object> map){
		ResultInfo result =new ResultInfo();
		
		// 这里我们判断请求中是否有name参数，如果有则放入session，没有则从session中取出name放入map
		HttpSession session = (HttpSession) map.get("session");
		if (map.get("name") != null) {
			Object name = map.get("name");
			session.addAttribute("name", name);
		} else {
			Object name = session.getAttribute("name");
			if (name != null) {
				map.put("name", name);
			}
		}
		
		result.setView("index");
		result.setResultMap(map);
		return result;
	}
}
