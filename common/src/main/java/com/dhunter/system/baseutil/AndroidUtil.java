package com.dhunter.system.baseutil;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by lgh on 2018/10/9.
 */

public class AndroidUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String getHideNumber(String phoneNumber){
        if(!TextUtils.isEmpty(phoneNumber)){
            String str = "****";
            StringBuilder sb = new StringBuilder(phoneNumber);
            sb.replace(3, 7, str);
            return sb.toString();

        }
        return "";
    }
}
