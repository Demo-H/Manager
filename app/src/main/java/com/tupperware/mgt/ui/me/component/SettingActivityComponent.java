package com.tupperware.mgt.ui.me.component;

import com.dhunter.common.AppComponent;
import com.dhunter.common.PerActivity;
import com.tupperware.mgt.ui.login.activities.LoginSelectActivity;
import com.tupperware.mgt.ui.login.module.LoginPresenterModule;
import com.tupperware.mgt.ui.me.activity.SettingActivity;
import com.tupperware.mgt.ui.me.module.SettingPresenterModule;

import dagger.Component;

/**
 * Created by dhunter on 2018/11/26.
 */
@PerActivity
@Component(dependencies = AppComponent.class , modules = SettingPresenterModule.class)
public interface SettingActivityComponent {
    void inject(SettingActivity activity);
}