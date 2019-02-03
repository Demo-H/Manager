package com.dhunter.system.eventCenter;

/**
 * Created by gohonglin on 2017/7/27.
 */

public class EventConst {
    public static class AUTOPLAY{
        public static final String EVENT_SOURCE_NAME = "auto_play_event";
        public static final int START_AUTO_PLAY = 1;
        public static final int STOP_AUTO_PLAY = 2;
    }

    public static class InViteCode{
        public static final String EVENT_SOURCE_NAME = "invite_code_event_source_name";
        public static final int UPDATE_TITLE_TAB_NUMBER = 1;
    }

    public static class ShopBuy{
        public static final String EVENT_SOURCE_NAME = "shop_buy_source_name";
        public static final int UPDATE_BUY_CONFIRM_TOTAL_MONEY = 1;
    }

    public static class BindCAccount{
        public static final String EVENT_SOURCE_NAME = "bind_account_source_name";
        public static final int GET_LOGIN_WECHAT_SUCCESS = 1;
        public static final int GET_LOGIN_WECHAT_USER_REFUSED = 2;
        public static final int GET_LOGIN_WECHAT_USER_CANCEL = 3;
    }
}
