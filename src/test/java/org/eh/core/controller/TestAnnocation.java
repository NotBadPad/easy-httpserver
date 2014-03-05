package org.eh.core.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 *
 * @author guojing
 * @date 2014-3-5
 */
public class TestAnnocation {

	@Test
	public void getAnnontion() {
		List<String> list = getPkgClass("org.eh.core.controller");
		System.out.println(Arrays.toString(list.toArray()));
	}

	public List<String> getPkgClass(String parkage) {
		String path = this.getClass().getResource("/").getPath() + parkage.replace(".", "/") + "/";
		List<String> list = new ArrayList<String>();
		
		File file = new File(path);
		for(String str :file.list()){
			if (str.endsWith(".class")) {
				list.add(parkage + "." + str.replace(".class", ""));
			} else if (str.indexOf(".") == -1) {
				list.addAll(getPkgClass(parkage + "." + str));
			}
		}

		return list;
	}
}
