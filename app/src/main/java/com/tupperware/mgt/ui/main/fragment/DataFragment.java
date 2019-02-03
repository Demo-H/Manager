package com.tupperware.mgt.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseFragment;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiResponse;
import com.tupperware.mgt.entity.datawindow.GetTargetKpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiXyzResponse;
import com.tupperware.mgt.entity.datawindow.KpiYMResponse;
import com.tupperware.mgt.http.DataWindowDataManager;
import com.tupperware.mgt.ui.datawindow.KPIPagerAdapter;
import com.tupperware.mgt.ui.datawindow.activity.KpiTargetActivity;
import com.tupperware.mgt.ui.datawindow.component.DaggerDataFragmentComponent;
import com.tupperware.mgt.ui.datawindow.contract.DataWindowContract;
import com.tupperware.mgt.ui.datawindow.module.DataWindowPresenterModule;
import com.tupperware.mgt.ui.datawindow.presenter.DataWindowPresenter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/19.
 */

public class DataFragment extends BaseFragment implements DataWindowContract.View {

    private static final String[] CHANNELS = new String[]{"销售净额","实际零售额","OIO","新开店","关店","分销商数","新招VIP会员数","单产（元）"};
    private List<String> mTitleList = Arrays.asList(CHANNELS);

    List<KpiYMResponse.ModelsBean> mDataList = new ArrayList<>();

    private View rootView = null;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.kpi_layout)
    LinearLayout kpiLayout;

    KPIPagerAdapter pagerAdapter;

    @Inject
    DataWindowPresenter dataWindowPresenter;

    public DataFragment() {
    }

    public static DataFragment newInstance() {
        DataFragment fragment = new DataFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
        return rootView;
    }

    @Override
    public void initLayout() {
        DaggerDataFragmentComponent.builder()
                .appComponent(getAppComponent())
                .dataWindowPresenterModule(new DataWindowPresenterModule(this, DataWindowDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        pagerAdapter = new KPIPagerAdapter(getActivity(),mTitleList);
        initMagicIndicator();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void requestData() {
        mDataList.clear();
        dataWindowPresenter.getKpiYMData();
        dataWindowPresenter.getKpiXyzYMData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data;
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setPadding(UIUtil.dip2px(getActivity(),15),0,UIUtil.dip2px(getActivity(),15),0);
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                simplePagerTitleView.setText(mTitleList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#c8eeed"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#1fbbb9"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#FFFFFF"));
                indicator.setVerticalPadding(UIUtil.dip2px(getActivity(),4));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void refreshKPIData(List<KpiYMResponse.ModelsBean> list) {
        if (list != null){
            mDataList.addAll(list);
            pagerAdapter.setData(mDataList);
            pagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refreshKPIXyzData(KpiXyzResponse.ModelBean bean) {
        KpiYMResponse.ModelsBean modelsBean = new KpiYMResponse.ModelsBean();
        modelsBean.setQuarterTrueAmt(bean.getAvgTotal());
        modelsBean.setQuarterPlanAmt(bean.getAvgX());
        modelsBean.setMonthTrueAmt(bean.getAvgY());
        modelsBean.setMonthPlanAmt(bean.getAvgZ());
        modelsBean.setUpdateDatetime(bean.getUpdateDatetime());
        modelsBean.setKpi("order_xyz");
        mDataList.add(modelsBean);

        pagerAdapter.setData(mDataList);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateTargetKpiData(GetTargetKpiResponse bean) {

    }

    @Override
    public void updateTargetKpiXyzData(List<GetTargetKpiXyzResponse.ModelBean> beanList) {

    }

    @OnClick({R.id.kpi_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kpi_layout:
                Intent intent = new Intent(getActivity(),KpiTargetActivity.class);
                getActivity().startActivity(intent);
                break;
            default:
                break;
        }
    }
}