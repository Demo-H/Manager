package com.tupperware.mgt.ui.login.module;

import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/11/26.
 */

@Module
public class LoginPresenterModule {
    private LoginContract.View view;
    private LoginDataManager mDataManager;

    public LoginPresenterModule(LoginContract.View view, LoginDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    LoginDataManager providerLoginDataManager() {
        return mDataManager;
    }

    @Provides
    LoginContract.View providerLoginContractView(){
        return view;
    }
}