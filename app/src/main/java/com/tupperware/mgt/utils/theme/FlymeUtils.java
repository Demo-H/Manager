package com.tupperware.mgt.utils.theme;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class FlymeUtils {
	private static final String KEY_BUILD_DISPLAY_ID = "ro.build.display.id";
	/**
	 * 判断系统是否大于Flyme2
	 *
	 * @return
	 */
	public static boolean isFlyme2() {
		String flyme = getSystemProperty(KEY_BUILD_DISPLAY_ID);
		if (!TextUtils.isEmpty(flyme) && flyme.contains("Flyme")) {
			return true;
		}
		return false;
	}
	/**
	 * 获取系统信息
	 *
	 * @param propName
	 * @return
	 */
	public static String getSystemProperty(String propName) {
		String line;
		BufferedReader input = null;
		try {
			Process p = Runtime.getRuntime().exec("getprop " + propName);
			input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
			line = input.readLine();
			input.close();
		} catch (Exception ex) {

			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {

				}
			}
		}
		return line;
	}
}
