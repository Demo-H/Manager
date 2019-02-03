package com.tupperware.mgt.ui.login.contract;

import com.tupperware.mgt.entity.login.EmployeeInfo;

/**
 * Created by dhunter on 2018/11/29.
 */

public class WechatAuthContract {
    public interface View {
        void setWeChatAuthSuccess(EmployeeInfo fullPersonalInfo);
        void loginSuccess();
        void setWeChatAuthFail();
        void hideDialog();
        void toast(String msg);
    }

    public interface Presenter {
        void WeChatAuth(String code);
    }
}
