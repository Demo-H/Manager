package com.tupperware.mgt.ui.datawindow.contract;

import com.tupperware.mgt.entity.datawindow.GetTargetKpiResponse;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiYMResponse;

import java.util.List;

/**
 * Created by umt041 on 2018/12/28.
 */
public class DataWindowContract {

    public interface View {
        void refreshKPIData(List<KpiYMResponse.ModelsBean> list);
        void refreshKPIXyzData(KpiXyzResponse.ModelBean bean);

        void updateTargetKpiData(GetTargetKpiResponse bean);
        void updateTargetKpiXyzData(List<GetTargetKpiXyzResponse.ModelBean> beanList);

        void hideDialog();
    }

    public interface Presenter {
        void getKpiYMData();
        void getKpiXyzYMData();

        void getTargetKpiData(String dataType,int year,int month);
        void getTargetKpiXyzData(String dataType,int year,int month);
    }
}
