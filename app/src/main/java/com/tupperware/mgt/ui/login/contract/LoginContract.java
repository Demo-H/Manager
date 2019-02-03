package com.tupperware.mgt.ui.login.contract;

import com.tupperware.mgt.entity.login.EmployeeInfo;

/**
 * Created by dhunter on 2018/11/26.
 */

public class LoginContract {
    public interface View {
        void setLoginResult();
        void hideDialog();
        void toast(String msg);
        void refreshPersonalInfo(EmployeeInfo info);
        void setHeaderHide();
    }

    public interface Presenter {
        void loginByAccount(String employeeId, String psw,boolean isAuto);
        void getPersonalInfobyJobId(String employeeId);
    }
}
