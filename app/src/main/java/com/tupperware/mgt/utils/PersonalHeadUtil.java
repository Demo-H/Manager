package com.tupperware.mgt.utils;

import com.tupperware.mgt.entity.login.EmployeeInfo;

/**
 * Created by dhunter on 2018/11/26.
 */

public class PersonalHeadUtil {

    public static String getPost(EmployeeInfo info) {
        StringBuilder stringBuilder = new StringBuilder();
        if (info.getpRegion() != null && !info.getpRegion().isEmpty()) {
            stringBuilder.append(info.getpRegion());
        }
        if (info.getpProvince() != null && !info.getpProvince().isEmpty()) {
            if(stringBuilder.length() > 0) {
                stringBuilder.append(" - ");
            }
            stringBuilder.append(info.getpProvince());
        }
        if (info.getpCityno() != null && !info.getpCityno().isEmpty()) {
            stringBuilder.append(info.getpCityno());
        }
        if (info.getpPosition() != null && !info.getpPosition().isEmpty()) {
            if(stringBuilder.length() > 0) {
                stringBuilder.append(" / ");
            }
            stringBuilder.append(info.getpPosition());
        }
        return stringBuilder.toString();
    }
}
