package com.tupperware.mgt.ui.login.contract;

import com.tupperware.mgt.entity.login.EmployeeInfo;

/**
 * Created by dhunter on 2018/11/27.
 */

public class SmsCodeLoginContract {
    public interface View {
        void setLoginResult();
        void hideDialog();
        void toast(String msg);
        void refreshPersonalInfo(EmployeeInfo info);
        void setHeaderHide();
        void setSmsCode();
        void setSMSCodeError();
    }

    public interface Presenter {
        void LoginByMobile(String mobile, String smsCode);
        void getPersonalInfobyMobile(String mobile);
        void getSmsCode(String mobile);
    }
}
