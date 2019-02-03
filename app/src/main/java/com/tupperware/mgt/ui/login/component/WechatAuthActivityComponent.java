package com.tupperware.mgt.ui.login.component;

import com.dhunter.common.AppComponent;
import com.dhunter.common.PerActivity;
import com.tupperware.mgt.ui.login.activities.WechatAuthActivity;
import com.tupperware.mgt.ui.login.module.WechatAuthPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/11/29.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = WechatAuthPresenterModule.class)
public interface WechatAuthActivityComponent {
    void inject(WechatAuthActivity activity);
}