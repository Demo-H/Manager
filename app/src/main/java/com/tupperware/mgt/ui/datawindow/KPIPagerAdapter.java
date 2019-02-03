package com.tupperware.mgt.ui.datawindow;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tupperware.mgt.R;
import com.tupperware.mgt.entity.datawindow.KpiYMResponse;
import com.tupperware.mgt.utils.MathUtil;

import java.util.Calendar;
import java.util.List;

public class KPIPagerAdapter extends PagerAdapter {
    private List<KpiYMResponse.ModelsBean> mDataList;
    private List<String> mTitleList;
    private Context context;

    public KPIPagerAdapter(Context context, List<String> titles) {
        this.context = context;
        mTitleList = titles;
    }

    public void setData(List<KpiYMResponse.ModelsBean> list){
        this.mDataList = list;
    }

    @Override
    public int getCount() {
        return mTitleList == null ? 0 : mTitleList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_progress,null);
        view.setTag(position);
        initData(position,view);
        container.addView(view);
        return view;
    }

    private void initData(int position,View view){
        if (mDataList == null || mDataList.size() <= position){
            return;
        }

        TextView quarterPercentTv = (TextView) view.findViewById(R.id.quarter_percent_tv);
        TextView quarterCurrentTv = (TextView) view.findViewById(R.id.quarter_current_tv);
        TextView quarterPlanTv = (TextView) view.findViewById(R.id.quarter_plan_tv);
        TextView monthPercentTv = (TextView) view.findViewById(R.id.month_percent_tv);
        TextView monthCurrentTv = (TextView) view.findViewById(R.id.month_current_tv);
        TextView monthPlanTv = (TextView) view.findViewById(R.id.month_plan_tv);
        TextView refreshTv = (TextView) view.findViewById(R.id.refresh_time);

        KpiYMResponse.ModelsBean item = getBean(position);
        if (item == null){
            return ;
        }
        if (position != 7){
            quarterPercentTv.setText(item.getQuarterPreAmt() + "%");
            quarterCurrentTv.setText(MathUtil.formatNumberWithComma(item.getQuarterTrueAmt()));
            quarterPlanTv.setText("目标：" + MathUtil.formatNumberWithComma(item.getQuarterPlanAmt()));

            monthPercentTv.setText(item.getMonthPreAmt() + "%");
            monthCurrentTv.setText(MathUtil.formatNumberWithComma(item.getMonthTrueAmt()));
            monthPlanTv.setText("目标：" + MathUtil.formatNumberWithComma(item.getMonthPlanAmt()));
        }else {
            TextView quarterTipTv = (TextView) view.findViewById(R.id.quarter_tip_tv);
            quarterTipTv.setText("平均单产");
            quarterPercentTv.setVisibility(View.GONE);
            quarterCurrentTv.setText(MathUtil.formatNumberWithComma(item.getQuarterTrueAmt()));
            quarterPlanTv.setText("前20%单产：" + MathUtil.formatNumberWithComma(item.getQuarterPlanAmt()));

            TextView monthTipTv = (TextView) view.findViewById(R.id.month_tip_tv);
            monthTipTv.setText("中间单产60%");
            monthPercentTv.setVisibility(View.GONE);
            monthCurrentTv.setText(MathUtil.formatNumberWithComma(item.getMonthTrueAmt()));
            monthPlanTv.setText("后20%单产：" + MathUtil.formatNumberWithComma(item.getMonthPlanAmt()));
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(item.getUpdateDatetime());
        refreshTv.setText("数据更新于" + cal.get(Calendar.YEAR) + "."+(cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.DAY_OF_MONTH));
    }

    private KpiYMResponse.ModelsBean getBean(int position){
        for (KpiYMResponse.ModelsBean modelsBean :mDataList){
            String kpi = modelsBean.getKpi();
            if (position == 0 && "net_amt".endsWith(kpi)){//销售净额
                return modelsBean;
            } else if (position == 1 && "actual_amt".endsWith(kpi)){//实际零售额
                return modelsBean;
            }else if (position == 2 && "cnt_oio".endsWith(kpi)){//OIO
                return modelsBean;
            }else if (position == 3 && "new_outlet".endsWith(kpi)){//新开店
                return modelsBean;
            }else if (position == 4 && "close_outlet".endsWith(kpi)){//关店
                return modelsBean;
            }else if (position == 5 && "cnt_db".endsWith(kpi)){//分销商数
                return modelsBean;
            }else if (position == 6 && "new_member_vip".endsWith(kpi)){//新招VIP会员数
                return modelsBean;
            }else if (position == 7 && "order_xyz".endsWith(kpi)){//单产
                return modelsBean;
            }
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

}
