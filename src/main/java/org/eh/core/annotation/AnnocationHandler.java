package org.eh.core.annotation;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eh.core.common.Constants;

/**
 * 注解处理类
 * @author guojing
 * @date 2014-3-5
 */
public class AnnocationHandler {

	/**
	 * 将所有注解Controller加入Constants.UrlClassMap
	 * @param parkage 类名（包含包路径）
	 */
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
	 * 获取指定包下的所有类名（包含包名）
	 * @param parkage 指定包名
	 * @return
	 */
	public List<String> getPkgClass(String parkage) {
		String path = Constants.CLASS_PATH + parkage.replace(".", "/") + "/";
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
	
	/**
	 * 获取类的指定方法
	 * @param c
	 * @param methodName
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Method getMethod(Class c, String methodName) throws NoSuchMethodException,
			SecurityException {
		Method method = c.getMethod(methodName, Map.class);
		return method.isAnnotationPresent(RequestMapping.class) ? method : null;
	}
}
