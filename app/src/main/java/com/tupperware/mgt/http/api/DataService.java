package com.tupperware.mgt.http.api;

import com.tupperware.mgt.entity.datawindow.EmptyRequest;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiRequest;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiResponse;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiYMResponse;
import com.tupperware.mgt.entity.login.LoginRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by umt041 on 2018/12/28.
 */
public interface DataService {

    /** 获取当前年月的kpi数据 **/
    @POST("backend/manage/kpiTarget/getKpiYM")
    Observable<KpiYMResponse> getKpiYM(@Body EmptyRequest request);

    /** 获取当前年月kpi单产数据 **/
    @POST("backend/manage/kpiTarget/getKpiXyzYM")
    Observable<KpiXyzResponse> getKpiXyzYM(@Body EmptyRequest request);

    /** 获取当前年月kpi数据 **/
    @POST("backend/manage/kpiTarget/getKpi")
    Observable<GetTargetKpiResponse> getTargetKpi(@Body GetTargetKpiRequest request);

    /** 获取当前年月kpi单产数据 **/
    @POST("backend/manage/kpiTarget/getKpiXyz")
    Observable<GetTargetKpiXyzResponse> getTargetKpiXyz(@Body GetTargetKpiRequest request);
}
