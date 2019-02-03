package com.tupperware.mgt.ui.login.contract;

/**
 * Created by dhunter on 2018/11/26.
 */

public class ResetPasswordContract {
    public interface View {
        void setResetSuccess();
        void hideDialog();
        void toast(String msg);
    }

    public interface Presenter {
        void modifiedPsw(String newPswString);
    }
}
