package com.tupperware.mgt.ui.datawindow.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhunter.common.tablayout.CommonTabLayout;
import com.dhunter.common.tablayout.listener.CustomTabEntity;
import com.dhunter.common.tablayout.listener.OnTabSelectListener;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.entity.TabEntity;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiResponse;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiYMResponse;
import com.tupperware.mgt.http.DataWindowDataManager;
import com.tupperware.mgt.ui.datawindow.KpiTargetAdapter;
import com.tupperware.mgt.ui.datawindow.component.DaggerKpiTargetActivityComponent;
import com.tupperware.mgt.ui.datawindow.contract.DataWindowContract;
import com.tupperware.mgt.ui.datawindow.module.DataWindowPresenterModule;
import com.tupperware.mgt.ui.datawindow.presenter.DataWindowPresenter;
import com.tupperware.mgt.utils.MathUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LandscapeKpiTargetActivity extends BaseActivity {

    private List<String> mTitleList = new ArrayList<>();

    @BindView(R.id.recyclerview_content)
    RecyclerView contentRecyclerView;

    KpiTargetAdapter kpiTargetAdapter;

    Map<String,ArrayList<ArrayList<String>>> listMap = new HashMap();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.land_activity_kpi_target;
    }

    @Override
    protected void initLayout() {

        for (int i = 0;i< 1 ; i++){
            mTitleList.add(i+"月");
        }

        kpiTargetAdapter = new KpiTargetAdapter(this,R.layout.land_adapter_kpi_target,mTitleList);
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentRecyclerView.setAdapter(kpiTargetAdapter);
    }



    @Override
    protected void requestData() {
        listMap = (Map<String, ArrayList<ArrayList<String>>>) getIntent().getSerializableExtra("dataMap");
        if (listMap != null){
            kpiTargetAdapter.setLandscape(true);
            kpiTargetAdapter.setKpiTabDataMap(listMap);
            kpiTargetAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.close_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_image:
                finish();
                break;

        }
    }
}
