package com.tupperware.mgt.ui.login.module;

import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.WechatBindTelContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/11/27.
 */

@Module
public class WechatBindTelPresenterModule {
    private WechatBindTelContract.View view;
    private LoginDataManager mDataManager;

    public WechatBindTelPresenterModule(WechatBindTelContract.View view, LoginDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    LoginDataManager providerLoginDataManager() {
        return mDataManager;
    }

    @Provides
    WechatBindTelContract.View providerWechatBindTelContractView(){
        return view;
    }
}