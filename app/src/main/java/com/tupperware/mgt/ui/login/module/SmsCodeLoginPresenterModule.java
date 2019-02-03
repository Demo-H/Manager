package com.tupperware.mgt.ui.login.module;

import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.SmsCodeLoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/11/27.
 */

@Module
public class SmsCodeLoginPresenterModule {
    private SmsCodeLoginContract.View view;
    private LoginDataManager mDataManager;

    public SmsCodeLoginPresenterModule(SmsCodeLoginContract.View view, LoginDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    LoginDataManager providerLoginDataManager() {
        return mDataManager;
    }

    @Provides
    SmsCodeLoginContract.View providerSmsCodeLoginContractView(){
        return view;
    }
}