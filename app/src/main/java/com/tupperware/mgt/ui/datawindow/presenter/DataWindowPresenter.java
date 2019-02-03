package com.tupperware.mgt.ui.datawindow.presenter;

import android.util.Log;

import com.dhunter.common.base.BasePresenter;
import com.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.mgt.entity.datawindow.EmptyRequest;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiRequest;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiResponse;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiYMResponse;
import com.tupperware.mgt.entity.login.LoginRequest;
import com.tupperware.mgt.http.DataWindowDataManager;
import com.tupperware.mgt.ui.datawindow.contract.DataWindowContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 数据窗
 * Created by umt041 on 2018/12/29.
 */
public class DataWindowPresenter extends BasePresenter implements DataWindowContract.Presenter {

    private DataWindowContract.View mView;
    private DataWindowDataManager mDataManager;

    @Inject
    public DataWindowPresenter(DataWindowDataManager dataManager, DataWindowContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }

    /**
     * 获取当前年月的kpi数据
     **/
    @Override
    public void getKpiYMData() {
        mDataManager.setHeader(false);
        mDataManager.getKpiYMData(new ErrorDisposableObserver<KpiYMResponse>() {
            @Override
            public void onNext(KpiYMResponse bean) {
                if (bean != null){
                    mView.refreshKPIData(bean.getModels());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {

            }
        },new EmptyRequest());
    }

    /**
     * 获取kpi单产数据
     */
    @Override
    public void getKpiXyzYMData() {
        mDataManager.getKpiXyzYMData(new ErrorDisposableObserver<KpiXyzResponse>() {
            @Override
            public void onNext(KpiXyzResponse bean) {
                if (bean != null && bean.getModel() != null){
                    mView.refreshKPIXyzData(bean.getModel());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {

            }
        },new EmptyRequest());
    }

    /**
     * 获取达成kpi
     * @param dataType
     * @param month
     */
    @Override
    public void getTargetKpiData(String dataType,int year,int month) {
        mDataManager.getTargetKpiData(new ErrorDisposableObserver<GetTargetKpiResponse>() {
            @Override
            public void onNext(GetTargetKpiResponse bean) {
                if (bean.isSuccess()){
                    mView.updateTargetKpiData(bean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
            }

            @Override
            public void onComplete() {
                mView.hideDialog();
            }
        },new GetTargetKpiRequest(dataType,year,month));
    }

    /**
     * 获取达成KPI 单产
     */
    @Override
    public void getTargetKpiXyzData(String dataType,int year,int month) {
        mDataManager.getTargetKpiXyzData(new ErrorDisposableObserver<GetTargetKpiXyzResponse>() {
            @Override
            public void onNext(GetTargetKpiXyzResponse bean) {
                if (bean.isSuccess()){
                    mView.updateTargetKpiXyzData(bean.getModels());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {


            }
        },new GetTargetKpiRequest(dataType,2018,12));
    }
}
