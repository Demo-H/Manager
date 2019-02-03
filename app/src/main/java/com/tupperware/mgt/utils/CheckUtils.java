package com.tupperware.mgt.utils;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dhunter on 2018/2/24.
 */

public class CheckUtils {
    /**
     * 判断是否是手机号码
     * @param number
     * @return
     */
    public static boolean isPhoneNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        if (number.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(number.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[0-9])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(number);
            return m.matches();
        }
        return false;
    }
}
