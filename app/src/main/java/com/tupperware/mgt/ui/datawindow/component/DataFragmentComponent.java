package com.tupperware.mgt.ui.datawindow.component;

import com.dhunter.common.AppComponent;
import com.dhunter.common.PerActivity;
import com.dhunter.common.PerFragment;
import com.tupperware.mgt.ui.datawindow.module.DataWindowPresenterModule;
import com.tupperware.mgt.ui.login.activities.LoginActivity;
import com.tupperware.mgt.ui.login.module.LoginPresenterModule;
import com.tupperware.mgt.ui.main.fragment.DataFragment;

import dagger.Component;


@PerFragment
@Component(dependencies = AppComponent.class , modules = DataWindowPresenterModule.class)
public interface DataFragmentComponent {
    void inject(DataFragment fragment);
}