package com.tupperware.mgt.ui.login.activities;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhunter.common.config.GlobalConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.entity.login.EmployeeInfo;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.main.MainActivity;
import com.tupperware.mgt.ui.login.component.DaggerResetPasswordActivityComponent;
import com.tupperware.mgt.ui.login.contract.ResetPasswordContract;
import com.tupperware.mgt.ui.login.module.ResetPasswordPresenterModule;
import com.tupperware.mgt.ui.login.presenter.ResetPasswordPresenter;
import com.tupperware.mgt.utils.ActivityManager;
import com.tupperware.mgt.utils.ObjectUtil;
import com.tupperware.mgt.utils.PersonalHeadUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/16.
 * 首次登录需要重置密码
 */

public class ResetPasswordActivity extends BaseActivity implements ResetPasswordContract.View{

    @BindView(R.id.logo)
    TextView mLogo;
    @BindView(R.id.personal_info)
    LinearLayout mPersonalInfo;
    @BindView(R.id.head_img)
    SimpleDraweeView mHeadImag;
    @BindView(R.id.name)
    TextView mNickName;
    @BindView(R.id.user_post)
    TextView mUerPost;
    @BindView(R.id.new_psw)
    EditText mNewPsw;
    @BindView(R.id.new_psw_again)
    EditText mNewPswAgain;
    @BindView(R.id.new_psw_check)
    ImageView mNewPswCheck;
    @BindView(R.id.new_psw_again_check)
    ImageView mNewPswAgainCheck;
    @BindView(R.id.login_use)
    Button mLogin;

    private String newPswString;
    private String newPswAgainString;
    private int defaut_psw_min_length = 6;
    private EmployeeInfo info;

    @Inject
    ResetPasswordPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_psw;
    }

    @Override
    protected void initLayout() {
        DaggerResetPasswordActivityComponent.builder()
                .appComponent(getAppComponent())
                .resetPasswordPresenterModule(new ResetPasswordPresenterModule(this, LoginDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        ActivityManager.getInstance().addActivity(this);
        String infoJson = mDataManager.getSPData(GlobalConfig.LOGIN_EMPLOYEE_INFO);
//        KpiYMResponse response = ObjectUtil.fromJson(infoJson, KpiYMResponse.class);
//        if(response != null && response.getModel() != null) {
//            info = response.getModel();
//        }
        info = ObjectUtil.fromJson(infoJson, EmployeeInfo.class);
        mLogo.setVisibility(View.GONE);
        mPersonalInfo.setVisibility(View.VISIBLE);
        refreshPersonalInfo();
        addWatcherListener();
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.login_use})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_use:
                resetPswAndLogin();
                break;
        }
    }


    @Override
    public void setResetSuccess() {
        Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
        startActivity(intent);
        ActivityManager.getInstance().exit();
    }

    private void addWatcherListener() {
        mNewPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= defaut_psw_min_length) {
                    mNewPswCheck.setVisibility(View.VISIBLE);
                    mNewPswCheck.setSelected(true);
                } else {
                    mNewPswCheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNewPswAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= defaut_psw_min_length) {
                    mNewPswAgainCheck.setVisibility(View.VISIBLE);
                    if(checkPswEqu(s)) {
                        mNewPswAgainCheck.setSelected(true);
                    } else {
                        mNewPswAgainCheck.setSelected(false);
                    }
                } else {
                    mNewPswAgainCheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean checkPswEqu(CharSequence s) {
        newPswString = mNewPsw.getText().toString().trim();
        if(s.toString().equals(newPswString)) {
            return true;
        } else {
            return false;
        }
    }

    private void refreshPersonalInfo() {
        if(info != null) {
//        mHeadImag.setImageURI("");
            mNickName.setText(info.getpName());
            mUerPost.setText(PersonalHeadUtil.getPost(info));
        }
    }

    private void resetPswAndLogin() {
        newPswString = mNewPsw.getText().toString().trim();
        newPswAgainString = mNewPswAgain.getText().toString().trim();
        if(newPswString.length() < defaut_psw_min_length) {
            toast(getResources().getString(R.string.psw_should_set_six_length));
            return;
        } else if(!newPswString.equals(newPswAgainString)) {
            toast(getResources().getString(R.string.psw_put_different));
            return;
        } else {
            showDialog();
            mPresenter.modifiedPsw(newPswString);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
    }
}
