package com.tupperware.mgt.ui.datawindow.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dhunter.common.tablayout.CommonTabLayout;
import com.dhunter.common.tablayout.listener.CustomTabEntity;
import com.dhunter.common.tablayout.listener.OnTabSelectListener;
import com.dhunter.common.utils.DisplayUtils;
import com.rmondjone.locktableview.DisplayUtil;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class KpiTargetActivity extends BaseActivity implements DataWindowContract.View {

    private String[] mTitles = {"月度","季度","年度"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private List<String> mTitleList = new ArrayList<>();

    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.right_image)
    ImageView mRightImage;

    @BindView(R.id.recyclerview_content)
    RecyclerView contentRecyclerView;

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.tl_8)
    CommonTabLayout commonTabLayout;

    KpiTargetAdapter kpiTargetAdapter;

    @Inject
    DataWindowPresenter dataWindowPresenter;

    Map<String,ArrayList<ArrayList<String>>> listMap = new HashMap();

    //当前年月
    int currentMonth = 0;
    int currentYear = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kpi_target;
    }

    @Override
    protected void initLayout() {
        DaggerKpiTargetActivityComponent.builder()
                .appComponent(getAppComponent())
                .dataWindowPresenterModule(new DataWindowPresenterModule(this, DataWindowDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        mTitle.setText("年KPI达成");
        mRightImage.setImageResource(R.mipmap.kpi_zoom_ic);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }

        for (int i = 1;i< 13 ; i++){
            mTitleList.add(i+"月");
        }

        initMagicIndicator();
        initTabLayout();
        kpiTargetAdapter = new KpiTargetAdapter(this,R.layout.adapter_kpi_target,mTitleList);
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentRecyclerView.setAdapter(kpiTargetAdapter);
    }



    @Override
    protected void requestData() {
        getKpiDataForType("mTotal",0);
    }

    /**
     * 从服务端获取所选时间段的kpi数据
     * @param dataType
     * @param month
     */
    private void getKpiDataForType(String dataType,int month){
        showDialog();
        listMap.clear();
        dataWindowPresenter.getTargetKpiData(dataType,currentYear,month);
        dataWindowPresenter.getTargetKpiXyzData(dataType,currentYear,month);
    }

    private void updateTargetAdapter(int position){
        mTitleList.clear();
        if (position == 0){
            for (int i = 1;i< 13 ; i++){
                mTitleList.add(i+"月");
            }
        }else if (position == 1){
            mTitleList.add("第一季度");
            mTitleList.add("第二季度");
            mTitleList.add("第三季度");
            mTitleList.add("第四季度");
        }else if (position == 2) {
            mTitleList.add(currentYear + "");
        }
    }

    /**
     * 初始化二级选择项
     */
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
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                simplePagerTitleView.setText(mTitleList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#b5c3c3"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));

                int widthPixels =DisplayUtils.getScreenWidthPixels(mContext);

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        magicIndicator.onPageSelected(index);
                        magicIndicator.onPageScrolled(index, 0.0F, 0);
                        //
                        int commonTabIndex = commonTabLayout.getCurrentTab();
                        if (commonTabIndex == 0){
                            getKpiDataForType("mTotal",index + 1);
                        } else if(commonTabIndex == 1){
                            getKpiDataForType("qTotal",(index + 1) * 3);
                        } else if(commonTabIndex == 2){
                            getKpiDataForType("yTotal",0);
                        }

                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#1fbbb9"));
                indicator.setVerticalPadding(UIUtil.dip2px(getActivity(),4));
                indicator.setHorizontalPadding(UIUtil.dip2px(getActivity(),15));
                return indicator;
            }
        });

        magicIndicator.setNavigator(commonNavigator);
    }

    /**
     * 初始化月份、季度、年度tab
     */
    private void initTabLayout(){
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                commonTabLayout.setCurrentTab(position);
                updateTargetAdapter(position);
                initMagicIndicator();
                if (position == 0){
                    getKpiDataForType("mTotal",0);
                } else if(position == 1){
                    getKpiDataForType("qTotal",0);
                } else if(position == 2){
                    getKpiDataForType("yTotal",0);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    @Override
    public void updateTargetKpiData(GetTargetKpiResponse bean) {
        Map<String,ArrayList<ArrayList<String>>> map = getTabDataMap(bean);
        kpiTargetAdapter.setKpiTabDataMap(map);
        kpiTargetAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshKPIData(List<KpiYMResponse.ModelsBean> list) {
    }

    @Override
    public void refreshKPIXyzData(KpiXyzResponse.ModelBean bean) {
    }

    /**
     * 更新单产数据
     * @param list
     */
    @Override
    public void updateTargetKpiXyzData(List<GetTargetKpiXyzResponse.ModelBean> list) {
        ArrayList<ArrayList<String>> mTableDatas;
        ArrayList<String> mRowDatas;
        if (list == null){
            return ;
        }
        //构造表头
        mTableDatas = new ArrayList<ArrayList<String>>();
        ArrayList<String> mfristData = new ArrayList<String>();
        mfristData.add("区域/省办");
        mfristData.add("平均单产");
        mfristData.add("中间60%");
        mfristData.add("前20%");
        mfristData.add("后20%");
        mTableDatas.add(mfristData);

        for (GetTargetKpiXyzResponse.ModelBean data :list) {
            mRowDatas = new ArrayList<String>();
            //省份、区域名称
            mRowDatas.add(data.getOrganName());

            //平均单产
            mRowDatas.add(MathUtil.formatNumberWithComma(data.getAvgTotal()));
            //中间60%单产
            mRowDatas.add(MathUtil.formatNumberWithComma(data.getAvgY()));
            //前20%单产
            mRowDatas.add(MathUtil.formatNumberWithComma(data.getAvgX()));
            //后20%单产
            mRowDatas.add(MathUtil.formatNumberWithComma(data.getAvgZ()));
            mTableDatas.add(mRowDatas);
        }
        listMap.put("order_xyz",mTableDatas);
    }

    /**
     * 从服务器返回数据封装kpi表格数据信息
     * @param bean
     * @return
     */
    private Map<String,ArrayList<ArrayList<String>>> getTabDataMap(GetTargetKpiResponse bean) {

        List<GetTargetKpiResponse.ModelsBean> models  = bean.getModels();
        if (models == null ){
            return null;
        }

        GetTargetKpiResponse.ExtraBean extraBean = bean.getExtra();
        if (extraBean != null ){
            if (currentYear == 0){
                currentYear = extraBean.getYear();
            }
            int month = extraBean.getMonth();
            int commonTabIndex = commonTabLayout.getCurrentTab();

            if (commonTabIndex == 0){
                 magicIndicator.onPageSelected(month - 1);
                 magicIndicator.onPageScrolled(month - 1, 0.0F, 0);
            } else if (commonTabIndex == 1 ){
                if (month < 4){
                    magicIndicator.onPageSelected(0);
                    magicIndicator.onPageScrolled(0, 0.0F, 0);
                } else if(month < 7){
                    magicIndicator.onPageSelected(1);
                    magicIndicator.onPageScrolled(1, 0.0F, 0);
                } else if (month < 10){
                    magicIndicator.onPageSelected(2);
                    magicIndicator.onPageScrolled(2, 0.0F, 0);
                } else{
                    magicIndicator.onPageSelected(3);
                    magicIndicator.onPageScrolled(3, 0.0F, 0);
                }
            }


        }


        ArrayList<ArrayList<String>> mTableDatas;
        ArrayList<String> mRowDatas;
        for (GetTargetKpiResponse.ModelsBean model : models){
            //构造表头
            mTableDatas = new ArrayList<ArrayList<String>>();
            ArrayList<String> mfristData = new ArrayList<String>();
            mfristData.add("区域/省办");
            mfristData.add("实际完成");
            mfristData.add("目标");
            mfristData.add("差额");
            mfristData.add("达标率");
            mTableDatas.add(mfristData);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(model.getUpdateDatetime());
            kpiTargetAdapter.setRefreshTime("数据更新于" + cal.get(Calendar.YEAR) + "."+(cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.DAY_OF_MONTH));

            List<GetTargetKpiResponse.ModelsBean.AnlVKpiStatusRptListBean> list =
                    model.getAnlVKpiStatusRptList();
            if (list == null){
                break;
            }
            for (GetTargetKpiResponse.ModelsBean.AnlVKpiStatusRptListBean data :list) {
                mRowDatas = new ArrayList<String>();
                //省份、区域名称
                mRowDatas.add(data.getOrganName());

                if ("net_amt".equals(model.getKpi()) || "actual_amt".equals(model.getKpi())){
                    //实际
                    mRowDatas.add(MathUtil.formatNumberWithComma(data.getTrueAmt()/1000));
                    //目标
                    mRowDatas.add(MathUtil.formatNumberWithComma(data.getPlanAmt()/1000));
                    //差额
                    mRowDatas.add(MathUtil.formatNumberWithComma(data.getDiffAmt()/1000));
                } else{
                    //实际
                    mRowDatas.add(MathUtil.formatNumberWithComma(data.getTrueAmt()));
                    //目标
                    mRowDatas.add(MathUtil.formatNumberWithComma(data.getPlanAmt()));
                    //差额
                    mRowDatas.add(MathUtil.formatNumberWithComma(data.getDiffAmt()));
                }
                //完成率
                mRowDatas.add(MathUtil.formatNumberWithComma(data.getPreAmt()) + "%");
                mTableDatas.add(mRowDatas);
            }
            listMap.put(model.getKpi(),mTableDatas);
        }
        return listMap;
    }

    @OnClick({R.id.left_back,R.id.right_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.right_image:
                Intent intent = new Intent(this,LandscapeKpiTargetActivity.class);
                intent.putExtra("dataMap", (Serializable) listMap);
                startActivity(intent);
                break;
        }
    }
}
