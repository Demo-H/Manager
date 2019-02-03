package com.tupperware.mgt.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhunter.common.config.GlobalConfig;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseFragment;
import com.tupperware.mgt.entity.login.EmployeeInfo;
import com.tupperware.mgt.utils.ObjectUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/11/19.
 */

public class MessageFragment extends BaseFragment {

    private View rootView = null;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.content_tv)
    TextView contentTv;

    public MessageFragment() {
    }

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
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
        mTitle.setText("消息");
        mLeftBack.setVisibility(View.GONE);
        mRightNext.setVisibility(View.GONE);

        String infoJson = mDataManager.getSPData(GlobalConfig.LOGIN_EMPLOYEE_INFO);
        EmployeeInfo info = ObjectUtil.fromJson(infoJson, EmployeeInfo.class);
        if (info != null){
            contentTv.setText("您好，"+info.getpName()+"！欢迎使用特百惠.慧管理！");
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }
}