package com.tupperware.mgt.ui.login.contract;

import com.tupperware.mgt.entity.login.EmployeeInfo;

import java.util.Map;

/**
 * Created by dhunter on 2018/11/26.
 */

public class ForgetPswContract {

    public interface View {
        void setForgetResetResult();
        void hideDialog();
        void toast(String msg);
        void refreshPersonalInfo(EmployeeInfo info);
        void setHeaderHide();
        void setSmsCode();
        void setSMSCodeError();
    }

    public interface Presenter {
        void forgetPsw(Map<String, Object> maps);
        void getPersonalInfobyMobile(String mobile);
        void getSmsCode(String mobile);
    }
}
