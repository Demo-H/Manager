package com.tupperware.mgt.ui.login.module;

import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.WechatAuthContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/11/29.
 */

@Module
public class WechatAuthPresenterModule {
    private WechatAuthContract.View view;
    private LoginDataManager mDataManager;

    public WechatAuthPresenterModule(WechatAuthContract.View view, LoginDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    LoginDataManager providerLoginDataManager() {
        return mDataManager;
    }

    @Provides
    WechatAuthContract.View providerWechatAuthContractView(){
        return view;
    }
}