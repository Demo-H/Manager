package com.tupperware.mgt.ui.login.component;

import com.dhunter.common.AppComponent;
import com.dhunter.common.PerActivity;
import com.tupperware.mgt.ui.login.activities.SmsCodeLoginActivity;
import com.tupperware.mgt.ui.login.module.SmsCodeLoginPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/11/27.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = SmsCodeLoginPresenterModule.class)
public interface SmsCodeLoginActivityComponent {
    void inject(SmsCodeLoginActivity activity);
}