package com.tupperware.mgt.ui.login.contract;

import com.tupperware.mgt.entity.login.EmployeeInfo;

/**
 * Created by dhunter on 2018/11/27.
 */

public class WechatBindTelContract {
    public interface View {
        void bindResult();
        void hideDialog();
        void toast(String msg);
        void refreshPersonalInfo(EmployeeInfo info);
        void showWeChatInfo();
        void setSmsCode();
        void setSMSCodeError();
    }

    public interface Presenter {
        void wechatBindMobile(String mobile, String smsCode, EmployeeInfo fullPersonalInfo);
        void getPersonalInfobyMobile(String mobile);
        void getSmsCode(String mobile);
    }
}
