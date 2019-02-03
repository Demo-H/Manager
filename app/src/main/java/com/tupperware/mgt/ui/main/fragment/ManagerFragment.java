package com.tupperware.mgt.ui.main.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseFragment;
import com.tupperware.mgt.view.GridMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/19.
 */

public class ManagerFragment extends BaseFragment {

    private View rootView = null;
    @BindView(R.id.target_layout)
    LinearLayout mTargetLayout;
    @BindView(R.id.profit_layout)
    LinearLayout mProfitLayout;
    @BindView(R.id.psm_layout)
    LinearLayout mPsmLayout;

    public ManagerFragment() {
    }

    public static ManagerFragment newInstance() {
        ManagerFragment fragment = new ManagerFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
        requestData();
        return rootView;
    }

    @Override
    public void initLayout() {

    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_manager;
    }

    @OnClick({R.id.target_layout, R.id.profit_layout, R.id.psm_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.target_layout:
                break;
            case R.id.profit_layout:
                break;
            case R.id.psm_layout:
                break;
        }
    }
}
