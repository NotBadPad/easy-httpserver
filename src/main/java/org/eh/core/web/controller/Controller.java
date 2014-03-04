package org.eh.core.web.controller;

import java.util.Map;

import org.eh.core.model.ResultInfo;

/**
 *
 * @author guojing
 * @date 2014-3-3
 */
public interface Controller {

	/**
	 * 业务处理过程
	 * @param parms
	 */
	public ResultInfo process(Map<String, Object> parms);
}
