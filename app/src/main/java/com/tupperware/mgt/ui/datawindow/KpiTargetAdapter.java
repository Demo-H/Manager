package com.tupperware.mgt.ui.datawindow;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.dhunter.common.recycleview.BaseQuickAdapter;
import com.dhunter.common.recycleview.BaseViewHolder;
import com.dhunter.common.utils.DisplayUtils;
import com.rmondjone.locktableview.DisplayUtil;
import com.rmondjone.locktableview.LockTableView;
import com.rmondjone.xrecyclerview.ProgressStyle;
import com.tupperware.mgt.R;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by umt041 on 2019/1/9.
 */
public class KpiTargetAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    Map<String, ArrayList<ArrayList<String>>> kpiTabDataMap;

    ArrayList<ArrayList<String>> mTableDatas;

    String refreshTime;
    /**
     * 是否横屏模式
     */
    boolean isLandscape;

    @Override
    public int getItemCount() {
        return 1;
    }

    Context mContext;

    public KpiTargetAdapter(Context context, int layoutResId,List data) {
        super(layoutResId,data);
        mContext = context;
    }

    public void setKpiTabDataMap(Map<String, ArrayList<ArrayList<String>>> kpiTabDataMap) {
        this.kpiTabDataMap = kpiTabDataMap;
    }

    public void setRefreshTime(String refreshTime) {
        this.refreshTime = refreshTime;
    }

    public void setLandscape(boolean landscape) {
        isLandscape = landscape;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        if (kpiTabDataMap == null)
            return ;

        View refreshTimeView = helper.getView(R.id.refresh_time);
        if (refreshTimeView != null){
            helper.setText(R.id.refresh_time,refreshTime);
        }

        //销售净额
        LinearLayout mContentView = helper.getView(R.id.contentView);
        mTableDatas = kpiTabDataMap.get("net_amt");
        if (mTableDatas != null){
            final LockTableView mLockTableView = new LockTableView(mContext, mContentView, mTableDatas);
            setTabView(mLockTableView);
        } else{
            mContentView.removeAllViews();
        }

        //实际零售额
        mContentView = helper.getView(R.id.contentView1);
        mTableDatas = kpiTabDataMap.get("actual_amt");
        if (mTableDatas != null){
            final LockTableView mLockTableView1 = new LockTableView(mContext, mContentView, mTableDatas);
            setTabView(mLockTableView1);
        } else{
            mContentView.removeAllViews();
        }
        //OIO
        mContentView = helper.getView(R.id.contentView2);
        mTableDatas = kpiTabDataMap.get("cnt_oio");
        if (mTableDatas != null){
            final LockTableView mLockTableView2 = new LockTableView(mContext, mContentView, mTableDatas);
            setTabView(mLockTableView2);
        } else{
            mContentView.removeAllViews();
        }
        //新开店
        mContentView = helper.getView(R.id.contentView3);
        mTableDatas = kpiTabDataMap.get("new_outlet");
        if (mTableDatas != null){
            final LockTableView mLockTableView3 = new LockTableView(mContext, mContentView, mTableDatas);
            setTabView(mLockTableView3);
        } else{
            mContentView.removeAllViews();
        }
        //关店
        mContentView = helper.getView(R.id.contentView4);
        mTableDatas = kpiTabDataMap.get("close_outlet");
        if (mTableDatas != null){
            final LockTableView mLockTableView4 = new LockTableView(mContext, mContentView, mTableDatas);
            setTabView(mLockTableView4);
        } else{
            mContentView.removeAllViews();
        }
        //分销商数
        mContentView = helper.getView(R.id.contentView5);
        mTableDatas = kpiTabDataMap.get("cnt_db");
        if (mTableDatas != null){
            final LockTableView mLockTableView5 = new LockTableView(mContext, mContentView, mTableDatas);
            setTabView(mLockTableView5);
        } else{
            mContentView.removeAllViews();
        }
        //新招VIP会员数
        mContentView = helper.getView(R.id.contentView6);
        mTableDatas = kpiTabDataMap.get("new_member_vip");
        if (mTableDatas != null){
            final LockTableView mLockTableView6 = new LockTableView(mContext, mContentView, mTableDatas);
            setTabView(mLockTableView6);
        } else{
            mContentView.removeAllViews();
        }
        //单产
        mContentView = helper.getView(R.id.contentView7);
        mTableDatas = kpiTabDataMap.get("order_xyz");
        if (mTableDatas != null){
            final LockTableView mLockTableView7 = new LockTableView(mContext, mContentView, mTableDatas);
            setTabView(mLockTableView7);
        } else{
            mContentView.removeAllViews();
        }
    }

    private void setTabView(LockTableView mLockTableView) {
        if (!isLandscape){
            //竖屏
            mLockTableView.setLockFristColumn(true) //是否锁定第一列
                    .setLockFristRow(true) //是否锁定第一行
                    .setMaxColumnWidth(100) //列最大宽度
                    .setMinColumnWidth(53) //列最小宽度
//                .setColumnWidth(1,30) //设置指定列文本宽度
//                .setColumnWidth(2,20)
                    .setMinRowHeight(10)//行最小高度
                    .setMaxRowHeight(30)//行最大高度
                    .setTextViewSize(11) //单元格字体大小
                    .setFristRowBackGroudColor(R.color.white)//表头背景色
                    .setTableHeadTextColor(R.color.color_9ca9a9)//表头字体颜色
                    .setTableContentTextColor(R.color.border_color)//单元格字体颜色
                    .setCellPadding(5)//设置单元格内边距(dp)
                    .setNullableString("N/A") //空值替换值
                    .setOnItemSeletor(R.color.dashline_e8fcfc)//设置Item被选中颜色
                    .show(); //显示表格,此方法必须调用
        } else{
            //横屏

            int widthPixels =DisplayUtils.getScreenWidthPixels(mContext);
            int widthDp = DisplayUtil.px2dip(mContext,widthPixels);
            int perWidth = (widthDp - 150)/5;

            mLockTableView.setLockFristColumn(true) //是否锁定第一列
                    .setLockFristRow(true) //是否锁定第一行
                    .setMaxColumnWidth(500) //列最大宽度
                    .setMinColumnWidth(perWidth) //列最小宽度
                    .setMinRowHeight(10)//行最小高度
                    .setMaxRowHeight(30)//行最大高度
                    .setTextViewSize(13) //单元格字体大小
                    .setFristRowBackGroudColor(R.color.white)//表头背景色
                    .setTableHeadTextColor(R.color.color_9ca9a9)//表头字体颜色
                    .setTableContentTextColor(R.color.border_color)//单元格字体颜色
                    .setCellPadding(10)//设置单元格内边距(dp)
                    .setNullableString("N/A") //空值替换值
                    .setOnItemSeletor(R.color.dashline_e8fcfc)//设置Item被选中颜色
                    .show(); //显示表格,此方法必须调用
        }

        mLockTableView.getTableScrollView().setPullRefreshEnabled(false);
        mLockTableView.getTableScrollView().setLoadingMoreEnabled(false);
        mLockTableView.getTableScrollView().setRefreshProgressStyle(ProgressStyle.SquareSpin);
    }

}
