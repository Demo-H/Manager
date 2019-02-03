package com.tupperware.mgt.ui.main;

import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.dhunter.common.tablayout.CommonTabLayout;
import com.dhunter.common.tablayout.listener.CustomTabEntity;
import com.dhunter.common.tablayout.listener.OnTabSelectListener;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.base.BaseFragment;
import com.tupperware.mgt.entity.TabEntity;
import com.tupperware.mgt.ui.main.fragment.DataFragment;
import com.tupperware.mgt.ui.main.fragment.ManagerFragment;
import com.tupperware.mgt.ui.main.fragment.MeFragment;
import com.tupperware.mgt.ui.main.fragment.MessageFragment;
import com.tupperware.mgt.utils.StatusBarUtil;
import com.tupperware.mgt.utils.theme.FlymeUtils;
import com.tupperware.mgt.utils.theme.MIUIUtils;
import com.tupperware.mgt.utils.theme.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dhunter on 2018/11/19.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_layout)
    LinearLayout mainLayout;
    /**
     * 上次选中的fragment
     */
    int lastfragment = 0;
    @BindView(R.id.main_tl)
    CommonTabLayout mTablayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private String[] mTitles = {"微管理", "数据窗", "消息", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.nav_mgt_ic,R.mipmap.nav_data_ic,
            R.mipmap.nav_msg_ic, R.mipmap.nav_mine_ic};
    private int[] mIconSelectIds = {
            R.mipmap.nav_mgt_select_ic, R.mipmap.nav_data_select_ic,
            R.mipmap.nav_msg_select_ic, R.mipmap.nav_mine_select_ic};

    private ManagerFragment mManagerFragment;
    private DataFragment mDataFragment;
    private MessageFragment mMessageFragment;
    private MeFragment mMeFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initLayout() {
        setStateBarColor(com.dhunter.common.R.color.white);
        StatusBarUtils.setStatusBarTextColor(this,true);

        setFragments();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        lastfragment=0;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragments.get(0)).show(fragments.get(0)).commit();
        mTablayout.setTabData(mTabEntities);
        mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 切换fragment
     * @param index
     */
    private void switchFragment(int index)
    {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments.get(lastfragment));//隐藏上个Fragment
        if(!fragments.get(index).isAdded())
        {
            transaction.add(R.id.main_layout,fragments.get(index));
        }
        transaction.show(fragments.get(index)).commitAllowingStateLoss();
        lastfragment = index;

        if (index == 0){
            setStateBarColor(com.dhunter.common.R.color.white);
            StatusBarUtils.setStatusBarTextColor(this,true);
        }else{
            setStateBarColor(com.dhunter.common.R.color.color_1fbbb9);
            StatusBarUtils.setStatusBarTextColor(this,false);
        }
    }
    @Override
    protected void requestData() {

    }

    private void setFragments() {
        mManagerFragment = new ManagerFragment();
        mDataFragment = new DataFragment();
        mMessageFragment = new MessageFragment();
        mMeFragment = new MeFragment();
        fragments.add(mManagerFragment);
        fragments.add(mDataFragment);
        fragments.add(mMessageFragment);
        fragments.add(mMeFragment);
    }



}
