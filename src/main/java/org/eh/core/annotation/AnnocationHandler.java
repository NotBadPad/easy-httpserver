package org.eh.core.annotation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eh.core.common.Constants;

/**
 * 注解处理类
 * @author guojing
 * @date 2014-3-5
 */
public class AnnocationHandler {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void paserControllerAnnocation(String parkage) throws ClassNotFoundException {
		List<String> classlist = getPkgClass(parkage);
		for (String str : classlist) {
			Class c = Class.forName(str);
			if (c.isAnnotationPresent(Controller.class)) {
				Controller desc = (Controller) c.getAnnotation(Controller.class);
				Constants.UrlClassMap.put(desc.url(), str);
			}
		}
	}

	/**
	 * 获取指定标下的所有类名（包含包名）
	 * @param parkage 指定包名
	 * @return
	 */
	public List<String> getPkgClass(String parkage) {
		String path = this.getClass().getResource("/").getPath() + parkage.replace(".", "/") + "/";
		List<String> list = new ArrayList<String>();

		File file = new File(path);
		for (String str : file.list()) {
			if (str.endsWith(".class")) {
				list.add(parkage + "." + str.replace(".class", ""));
			} else if (str.indexOf(".") == -1) {
				list.addAll(getPkgClass(parkage + "." + str));
			}
		}

		return list;
	}
}
