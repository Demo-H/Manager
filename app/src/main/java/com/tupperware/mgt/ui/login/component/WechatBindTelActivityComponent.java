package com.tupperware.mgt.ui.login.component;

import com.dhunter.common.AppComponent;
import com.dhunter.common.PerActivity;
import com.tupperware.mgt.ui.login.activities.WechatBindTelActivity;
import com.tupperware.mgt.ui.login.module.WechatBindTelPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/11/27.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = WechatBindTelPresenterModule.class)
public interface WechatBindTelActivityComponent {
    void inject(WechatBindTelActivity activity);
}