package com.tupperware.mgt.ui.login.component;

import com.dhunter.common.AppComponent;
import com.dhunter.common.PerActivity;
import com.tupperware.mgt.ui.login.activities.LoginActivity;
import com.tupperware.mgt.ui.login.activities.LoginSelectActivity;
import com.tupperware.mgt.ui.login.module.LoginPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/11/26.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = LoginPresenterModule.class)
public interface LoginSelectActivityComponent {
    void inject(LoginSelectActivity activity);
}