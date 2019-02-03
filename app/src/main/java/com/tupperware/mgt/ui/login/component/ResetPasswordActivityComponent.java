package com.tupperware.mgt.ui.login.component;

import com.dhunter.common.AppComponent;
import com.dhunter.common.PerActivity;
import com.tupperware.mgt.ui.login.activities.ResetPasswordActivity;
import com.tupperware.mgt.ui.login.module.ResetPasswordPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/11/26.
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ResetPasswordPresenterModule.class)
public interface ResetPasswordActivityComponent {
    void inject(ResetPasswordActivity activity);
}