package org.eh.core.web.view;

import java.io.IOException;

import org.eh.core.common.Constants;
import org.eh.core.common.ReturnType;
import org.eh.core.model.ResultInfo;
import org.eh.core.util.IOUtil;
import org.eh.core.util.StringUtil;
import org.eh.core.util.VelocityUtil;

/**
 * 处理页面信息
 * @author guojing
 * @date 2014-3-3
 */
public class ViewHandler {

	/**
	 * 处理View模板,只提供建单变量(格式${XXX})替换,已废弃
	 * @return
	 */
	@Deprecated
	public String processView(ResultInfo resultInfo) {
		// 获取路径
		String path = analysisViewPath(resultInfo.getView());
		String content = "";
		if (IOUtil.isExist(path)) {
			content = IOUtil.readFile(path);
		}

		if (StringUtil.isEmpty(content)) {
			return "";
		}

		// 替换模板中的变量，替换符格式：${XXX}
		for (String key : resultInfo.getResultMap().keySet()) {
			String temp = "";
			if (null != resultInfo.getResultMap().get(key)) {
				temp = resultInfo.getResultMap().get(key).toString();
			}
			content = content.replaceAll("\\$\\{" + key + "\\}", temp);
		}

		return content;
	}

	/**
	 * 处理Velocity模板
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 */
	public String processVelocityView(ResultInfo resultInfo) throws IOException {
		if (StringUtil.isEmpty(resultInfo.getView())) {
			return "";
		}

		// 获取路径
		String path = analysisVelocityViewPath(resultInfo.getView());
		String content = VelocityUtil.mergeTemplate(path, resultInfo.getResultMap());
		
		if (StringUtil.isEmpty(content)) {
			return "";
		}

		return content;
	}

	/**
	 * 解析路径（根据Controller返回ResultInfo的view）,已废弃
	 * @param viewPath
	 * @return
	 */
	@Deprecated
	private String analysisViewPath(String viewPath) {
		String path = Constants.CLASS_PATH
				+ (Constants.VIEW_BASE_PATH == null ? "/" : Constants.VIEW_BASE_PATH)
				+ viewPath + ".page";
		return path;
	}

	/**
	 * 解析velocity路径（根据Controller返回ResultInfo的view）
	 */
	private String analysisVelocityViewPath(String viewPath) {
		String path = Constants.VIEW_BASE_PATH + "/"
				+ viewPath.replace(ReturnType.velocity.name() + ":", "") + ".vm";
		return path;
	}
}
