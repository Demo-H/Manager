package com.dhunter.common.config;

import java.util.Map;

/**
 * Created by dhunter on 2018/6/22.
 */

public class GlobalConfig {

    /** Common **/
    public static final boolean DEBUG = true;
    public static final String PLATFORM = "ANDROID";

    /** login Info **/
    public static Map<String, String> headers; //全局请求头
    public static final String LOGIN_TOKEN = "login_token";
    public static final String LOGIN_ACCOUNT = "login_account";
    public static final String LOGIN_MOBILE_PHONE = "login_mobile_phone";
//    public static final String LOGIN_PERSON_INFO = "login_person_info";
    public static final String LOGIN_EMPLOYEE_INFO = "login_employee_info";
//    public static final String LOGIN_WECHAT_INFO = "login_wechat_info";

    public static final String CURRENT_IS_LOGIN = "current_is_login";
    public static final String APP_FIRST_START = "app_first_start";
    public static boolean currentIsLogin = false;

    /** permission **/
    public static final String PACKAGE_URL_SCHEME = "package_url_scheme";

    /** loadingdialog **/
    public static final String LOADING_TIP = "loading_tip";

    public static String SHARE_PREFERENCE_FILE_NAME = "manager";
    public static final String SHARE_PREFERENCE_SETTING = "manager_setting";

    /**network **/
    public static int HTTP_READ_TIME_OUT = 30;
    public static int HTTP_CONNECT_TIME_OUT = 30;
    public static String BASE_URL = "http://admin.tupperware.net.cn/";
//    public static String BASE_URL = "http://admin.tbh.mobi/";
 }
