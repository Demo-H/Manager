package com.tupperware.mgt.http;

import com.dhunter.common.network.DataManager;
import com.tupperware.mgt.entity.datawindow.EmptyRequest;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiRequest;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiResponse;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiYMResponse;
import com.tupperware.mgt.entity.login.LoginRequest;
import com.tupperware.mgt.http.api.DataService;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * 数据窗的接口请求
 * Created by umt041 on 2018/12/29.
 */
public class DataWindowDataManager extends BaseDataManager{

    public DataWindowDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static DataWindowDataManager getInstance(DataManager dataManager){
        return new DataWindowDataManager(dataManager);
    }

    /** 获取当前年月的kpi数据 **/
    public Disposable getKpiYMData(DisposableObserver<KpiYMResponse> consumer, EmptyRequest request) {
        Observable observable = getService(DataService.class).getKpiYM(request);
        return changeIOToMainThread(observable, consumer);
    }

    /** 获取kpi的单产数据 **/
    public Disposable getKpiXyzYMData(DisposableObserver<KpiXyzResponse> consumer, EmptyRequest request) {
        Observable observable = getService(DataService.class).getKpiXyzYM(request);
        return changeIOToMainThread(observable, consumer);
    }

    /** 获取kpi的数据 **/
    public Disposable getTargetKpiData(DisposableObserver<GetTargetKpiResponse> consumer, GetTargetKpiRequest request) {
        Observable observable = getService(DataService.class).getTargetKpi(request);
        return changeIOToMainThread(observable, consumer);
    }

    /** 获取kpi的单产数据 **/
    public Disposable getTargetKpiXyzData(DisposableObserver<GetTargetKpiXyzResponse> consumer, GetTargetKpiRequest request) {
        Observable observable = getService(DataService.class).getTargetKpiXyz(request);
        return changeIOToMainThread(observable, consumer);
    }

}
