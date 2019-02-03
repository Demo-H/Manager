package com.tupperware.mgt.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseApplication;

/**
 * 自定义toast工具
 * 
 */

public class ToastUtil {

	private static Toast toast;
	private static Toast selftoast;

	/**
	 * 初始化Toast(消息，时间)
	 */
	private static Toast initToast(CharSequence message, int duration) {
		if (toast == null) {
			toast = Toast.makeText(BaseApplication.getInstance(), message, duration);
		} else {
			//设置文字
			toast.setText(message);
			//设置存续期间
			toast.setDuration(duration);
		}
		return toast;
	}

	public static void showL(String msg) {
		initToast(msg, Toast.LENGTH_LONG).show();
	}

	public static void showS(String msg) {
		initToast(msg, Toast.LENGTH_SHORT).show();
	}

	private static Toast selfToast(CharSequence message, int duration) {
		if(selftoast == null) {
			selftoast = new Toast(BaseApplication.getInstance());
		}
		View view = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.toast_custom, null);
		TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
		tv.setText(TextUtils.isEmpty(message) ? "" : message);
		selftoast.setView(view);
		selftoast.setGravity(Gravity.TOP, 0, 0);
		return selftoast;
	}

	public static void showSelfL(String msg) {
		selfToast(msg, Toast.LENGTH_LONG).show();
	}

	public static void showSelfS(String msg) {
		selfToast(msg, Toast.LENGTH_SHORT).show();
	}

}
