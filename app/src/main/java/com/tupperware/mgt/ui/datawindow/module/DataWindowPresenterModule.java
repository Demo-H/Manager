package com.tupperware.mgt.ui.datawindow.module;

import com.tupperware.mgt.http.DataWindowDataManager;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.datawindow.contract.DataWindowContract;
import com.tupperware.mgt.ui.login.contract.LoginContract;

import dagger.Module;
import dagger.Provides;


@Module
public class DataWindowPresenterModule {
    private DataWindowContract.View view;
    private DataWindowDataManager dataWindowDataManager;

    public DataWindowPresenterModule(DataWindowContract.View view, DataWindowDataManager mDataManager) {
        this.view = view;
        this.dataWindowDataManager = mDataManager;
    }

    @Provides
    DataWindowDataManager providerDataWindowDataManager() {
        return dataWindowDataManager;
    }

    @Provides
    DataWindowContract.View providerDataWindowContractView(){
        return view;
    }
}