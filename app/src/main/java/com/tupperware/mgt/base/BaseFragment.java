package com.tupperware.mgt.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dhunter.common.AppComponent;
import com.dhunter.common.GlobalAppComponent;
import com.dhunter.common.base.BaseRxFragment;
import com.dhunter.common.network.DataManager;

import butterknife.Unbinder;

/**
 * Created by dhunter on 2018/6/25.
 * 为添加butterknife控件，再封装一次
 */

public abstract class BaseFragment extends BaseRxFragment {

    protected DataManager mDataManager;
    protected Context mContext;
    protected Unbinder unbinder;
    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean mHasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean mIsFragmentVisible;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getAppComponent().getContext();
        mDataManager = getAppComponent().getDataManager();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initVariable();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        mHasCreateView = true;
        if(mIsFragmentVisible == isVisibleToUser)
            return;
        if (isVisibleToUser ) {
            onFragmentVisibleChange(true);
            mIsFragmentVisible = true;
        }else {
            onFragmentVisibleChange(false);
            mIsFragmentVisible = false;
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!mHasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            mIsFragmentVisible = true;
        }
    }

    private void initVariable() {
        mHasCreateView = false;
        mIsFragmentVisible = false;
    }


    protected AppComponent getAppComponent() {
        return GlobalAppComponent.getAppComponent();
    }

    protected void onFragmentVisibleChange(boolean isVisible) {
    }
}
