package com.tupperware.mgt.ui.datawindow.component;

import com.dhunter.common.AppComponent;
import com.dhunter.common.PerActivity;
import com.dhunter.common.PerFragment;
import com.tupperware.mgt.ui.datawindow.activity.KpiTargetActivity;
import com.tupperware.mgt.ui.datawindow.module.DataWindowPresenterModule;
import com.tupperware.mgt.ui.main.fragment.DataFragment;

import dagger.Component;


@PerActivity
@Component(dependencies = AppComponent.class , modules = DataWindowPresenterModule.class)
public interface KpiTargetActivityComponent {
    void inject(KpiTargetActivity kpiTargetActivity);
}