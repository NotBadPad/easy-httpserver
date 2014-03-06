package org.eh.core.web.view;

import org.eh.core.common.Constants;
import org.eh.core.model.ResultInfo;
import org.eh.core.util.FileUtil;
import org.eh.core.util.StringUtil;

/**
 * 处理页面信息
 * @author guojing
 * @date 2014-3-3
 */
public class ViewHandler {

	public String processView(ResultInfo resultInfo) {
		// 获取路径
		String path = analysisViewPath(resultInfo.getView());
		String content = "";
		if (FileUtil.isExist(path)) {
			content = FileUtil.readFile(path);
		}
		if (StringUtil.isEmpty(content)) {
			return "";
		}

		for (String key : resultInfo.getResultMap().keySet()) {
			String temp = "";
			if (null != resultInfo.getResultMap().get(key)) {
				temp = resultInfo.getResultMap().get(key).toString();
			}
			content = content.replaceAll("\\$\\{" + key + "\\}", temp);
		}

		return content;
	}

	private String analysisViewPath(String viewPath) {
		String path = this.getClass().getResource("/").getPath()
				+ (Constants.VIEW_BASE_PATH == null ? "/" : Constants.VIEW_BASE_PATH)
				+ viewPath + ".page";
		System.out.println(path);
		return path;
	}
}
