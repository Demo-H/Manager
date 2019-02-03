package com.tupperware.mgt.ui.me.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dhunter.common.config.GlobalConfig;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.activities.LoginSelectActivity;
import com.tupperware.mgt.ui.me.component.DaggerSettingActivityComponent;
import com.tupperware.mgt.ui.me.contract.SettingContract;
import com.tupperware.mgt.ui.me.module.SettingPresenterModule;
import com.tupperware.mgt.ui.me.presenter.SettingPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements SettingContract.View {
    @BindView(R.id.toolbar_title)
    TextView titleTv;

    @Inject
    SettingPresenter settingPresenter;

    @BindView(R.id.change_psw_layout)
    RelativeLayout changePswLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initLayout() {
        DaggerSettingActivityComponent.builder()
                .appComponent(getAppComponent())
                .settingPresenterModule(new SettingPresenterModule(this, LoginDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        titleTv.setText("设置");
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.change_psw_layout,R.id.exit_layout,R.id.left_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_layout:
                settingPresenter.exitApp();
                break;
            case R.id.change_psw_layout:
                Intent intent = new Intent(this,ChangePswActivity.class);
                startActivity(intent);
                break;
            case R.id.left_back:
                finish();
                break;

        }
    }

    @Override
    public void exitSuccess() {
        toast("退出成功！");

        mDataManager.remove(GlobalConfig.LOGIN_TOKEN);
        mDataManager.remove(GlobalConfig.LOGIN_EMPLOYEE_INFO);

        Intent intent = new Intent(this,LoginSelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void clearLoginInfo(){

    }

    @Override
    public void changePswSuccess() {

    }

    @Override
    public void updateImageCode(Bitmap bitmap) {

    }
}
