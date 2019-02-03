package com.tupperware.mgt.ui.login.module;

import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.ResetPasswordContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/11/26.
 */

@Module
public class ResetPasswordPresenterModule {
    private ResetPasswordContract.View view;
    private LoginDataManager mDataManager;

    public ResetPasswordPresenterModule(ResetPasswordContract.View view, LoginDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    LoginDataManager providerLoginDataManager() {
        return mDataManager;
    }

    @Provides
    ResetPasswordContract.View providerResetPasswordContractView(){
        return view;
    }
}