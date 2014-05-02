package org.eh.core.web.controller;

import java.util.Map;

import org.eh.core.model.ResultInfo;

/**
 * 主页对应的contoller
 * @author guojing
 */
public class IndexController implements Controller{
	
	public ResultInfo process(Map<String, Object> map){
		ResultInfo result =new ResultInfo();
		result.setView("index");
		result.setResultMap(map);
		return result;
	}
}
