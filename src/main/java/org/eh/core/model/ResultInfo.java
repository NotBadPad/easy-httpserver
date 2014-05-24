package org.eh.core.model;

import java.util.Map;

/**
 * Controller处理结果
 * @author guojing
 * @date 2014-3-3
 */
public class ResultInfo {

	private String view; // 对应页面路径
	private Map<String, Object> resultMap; // 返回参数值

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

}
