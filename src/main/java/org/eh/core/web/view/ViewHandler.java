package org.eh.core.web.view;

import org.eh.core.common.Constants;
import org.eh.core.model.ResultInfo;
import org.eh.core.util.FileUtil;

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

		for (String key : resultInfo.getResultMap().keySet()) {
			content = content.replaceAll("\\$\\{" + key + "\\}", resultInfo.getResultMap().get(key)
					.toString());
		}

		return content;
	}

	private String analysisViewPath(String viewPath) {
		String path = this.getClass().getResource("/").getPath() + Constants.VIEW_BASE_PATH
				+ viewPath + ".page";
		return path;
	}
}
