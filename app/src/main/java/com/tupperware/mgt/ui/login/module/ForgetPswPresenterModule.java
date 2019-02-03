package com.tupperware.mgt.ui.login.module;

import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.ForgetPswContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/11/26.
 */

@Module
public class ForgetPswPresenterModule {
    private ForgetPswContract.View view;
    private LoginDataManager mDataManager;

    public ForgetPswPresenterModule(ForgetPswContract.View view, LoginDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    LoginDataManager providerLoginDataManager() {
        return mDataManager;
    }

    @Provides
    ForgetPswContract.View providerForgetPswContractView(){
        return view;
    }
}