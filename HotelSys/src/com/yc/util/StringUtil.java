package com.yc.util;

public class StringUtil {

	//在表格中显示的数据 对象转化为字符串
	public static String objToString(Object obj){
		if (null==obj) {
			return null;
		}
		return obj.toString();
	}
}
