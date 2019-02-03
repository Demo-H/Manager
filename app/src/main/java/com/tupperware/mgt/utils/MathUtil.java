package com.tupperware.mgt.utils;

import java.text.DecimalFormat;

/**
 * Created by umt041 on 2018/12/26.
 */
public class MathUtil {

    /**
     * 格式化数字（每三位用逗号隔开）
     * @param number
     * @return
     */
    public static String formatNumberWithComma(double number){
        return new DecimalFormat(",###").format(number);
    }
}
