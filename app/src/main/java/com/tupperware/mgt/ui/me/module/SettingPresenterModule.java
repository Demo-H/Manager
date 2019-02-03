package com.tupperware.mgt.ui.me.module;

import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.LoginContract;
import com.tupperware.mgt.ui.me.contract.SettingContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/11/26.
 */

@Module
public class SettingPresenterModule {
    private SettingContract.View view;
    private LoginDataManager mDataManager;

    public SettingPresenterModule(SettingContract.View view, LoginDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    LoginDataManager providerLoginDataManager() {
        return mDataManager;
    }

    @Provides
    SettingContract.View providerSettingContractView(){
        return view;
    }
}