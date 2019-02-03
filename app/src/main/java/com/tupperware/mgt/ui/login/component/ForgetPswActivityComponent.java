package com.tupperware.mgt.ui.login.component;

import com.dhunter.common.AppComponent;
import com.dhunter.common.PerActivity;
import com.tupperware.mgt.ui.login.activities.ForgetPswActivity;
import com.tupperware.mgt.ui.login.module.ForgetPswPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/11/26.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ForgetPswPresenterModule.class)
public interface ForgetPswActivityComponent {
    void inject(ForgetPswActivity activity);
}