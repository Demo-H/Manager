package com.tupperware.mgt.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dhunter.common.config.GlobalConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseFragment;
import com.tupperware.mgt.entity.login.EmployeeInfo;
import com.tupperware.mgt.ui.me.activity.SettingActivity;
import com.tupperware.mgt.utils.ObjectUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/19.
 */

public class MeFragment extends BaseFragment {

    private View rootView = null;
    @BindView(R.id.tv_name)
    TextView nameTv;
    @BindView(R.id.tv_uid)
    TextView uidTv;
    @BindView(R.id.tv_position)
    TextView positionTv;
    @BindView(R.id.head_img)
    SimpleDraweeView headImageView;

    @BindView(R.id.rl_setting)
    RelativeLayout settingLayout;


    public MeFragment() {
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
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

        String infoJson = mDataManager.getSPData(GlobalConfig.LOGIN_EMPLOYEE_INFO);
        EmployeeInfo info = ObjectUtil.fromJson(infoJson, EmployeeInfo.class);
        if (info != null){
            nameTv.setText(info.getpName());
            uidTv.setText(info.getpUid());
            positionTv.setText(info.getpOrganization() + "-"+info.getpPosition());
            if (!TextUtils.isEmpty(info.getpWxHeadimg())){
                headImageView.setImageURI(info.getpWxHeadimg());
            }

        }

    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @OnClick({R.id.rl_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_setting:
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
                break;

        }
    }
}