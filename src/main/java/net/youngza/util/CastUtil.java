package net.youngza.util;

/**
 * 转型工具操作类
 * @author bj_yangsong 
 */
public class CastUtil {
	// 转换为String类型,默认返回""
	public static String castString(Object obj) {
		return castString(obj, "");
	}

	// 转换为String类型，指定默认返回值
	public static String castString(Object obj, String defaultVal) {
		return obj == null ? defaultVal : String.valueOf(obj);
	}

	// 转换为double类型，默认0.0
	public static double castDouble(Object obj) {
		return castDouble(obj, 0.0d);
	}

	// 转换为double了类型，指定默认返回值
	public static double castDouble(Object obj, double defaultVal) {
		double doubleVal = defaultVal;
		if (obj != null) {
			String strVal = castString(obj);
			if (StringUtil.isNotEmpty(strVal)) {
				try {
					doubleVal = Double.parseDouble(strVal);
				} catch (NumberFormatException e) {
					doubleVal = defaultVal;
					e.printStackTrace();
				}
			}
		}
		return doubleVal;
	}

	// 转换成long类型，默认返回0
	public static long castLong(Object obj) {
		return castLong(obj, 0l);
	}

	// 转换成long类型，指定默认返回值
	public static long castLong(Object obj, long defaultVal) {
		long longVal = defaultVal;
		if (obj != null) {
			String strVal = castString(obj);
			if (StringUtil.isNotEmpty(strVal)) {
				try {
					longVal = Long.parseLong(strVal);
				} catch (NumberFormatException e) {
					longVal = defaultVal;
					e.printStackTrace();
				}
			}
		}
		return longVal;
	}

	// 转换成int类型，默认返回0
	public static int castInt(Object obj) {
		return castInt(obj, 0);
	}

	// 转换成int类型，指定默认返回值
	public static int castInt(Object obj, int defaultVal) {
		int intVal = defaultVal;
		if (obj != null) {
			String strVal = castString(obj);
			if (StringUtil.isNotEmpty(strVal)) {
				try {
					intVal = Integer.parseInt(strVal);
				} catch (NumberFormatException e) {
					intVal = defaultVal;
					e.printStackTrace();
				}
			}
		}
		return intVal;
	}

	// 转换为boolean类型,默认是false
	public static boolean castBoolean(Object obj) {
		return castBoolean(obj, false);
	}

	// 转换为boolean类型，指定默认返回值,如果不是ture 字符串则都返回false
	public static boolean castBoolean(Object obj, boolean defaultVal) {
		if (obj != null) {
			String strVal = castString(obj);
			if (StringUtil.isNotEmpty(strVal)) {
				return Boolean.parseBoolean(strVal);
			}
		}
		return defaultVal;
	}
}
