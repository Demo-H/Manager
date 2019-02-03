package com.tupperware.mgt.ui.login.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhunter.common.utils.LeakUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.entity.login.EmployeeInfo;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.component.DaggerForgetPswActivityComponent;
import com.tupperware.mgt.ui.login.contract.ForgetPswContract;
import com.tupperware.mgt.ui.login.module.ForgetPswPresenterModule;
import com.tupperware.mgt.ui.login.presenter.ForgetPswPresenter;
import com.tupperware.mgt.utils.ActivityManager;
import com.tupperware.mgt.utils.CheckUtils;
import com.tupperware.mgt.utils.PersonalHeadUtil;
import com.tupperware.mgt.utils.SoftHideKeyBoardUtil;
import com.tupperware.mgt.utils.ToastUtil;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/19.
 * 忘记密码
 */

public class ForgetPswActivity extends BaseActivity implements ForgetPswContract.View{
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
    @BindView(R.id.user_tel)
    EditText mUserTel;
    @BindView(R.id.sms_code)
    EditText mSmsCode;
    @BindView(R.id.get_code_btn)
    Button mGetCode;
    @BindView(R.id.new_psw)
    EditText mNewPsw;
    @BindView(R.id.new_psw_again)
    EditText mNewPswAgain;
    @BindView(R.id.save)
    Button mSave;
    @BindView(R.id.user_tel_clear)
    ImageView mUserTelClear;
    @BindView(R.id.sms_code_clear)
    ImageView mSmsCodeClear;
    @BindView(R.id.new_psw_clear)
    ImageView mNewPswClear;
    @BindView(R.id.new_psw_again_clear)
    ImageView mNewPswAgainClear;

    @Inject
    ForgetPswPresenter mPresenter;

    private String mPhoneString;
    private String passwd;
    private String rePasswd;
    private String mSmsCodeString;
    private int time = 60;
    private boolean isrun = false; //标识短验控件线程销毁

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        SoftHideKeyBoardUtil.assistActivity(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_psw;
    }

    @Override
    protected void initLayout() {
        DaggerForgetPswActivityComponent.builder()
                .appComponent(getAppComponent())
                .forgetPswPresenterModule(new ForgetPswPresenterModule(this, LoginDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        ActivityManager.getInstance().addActivity(this);
        addWatcherListener();
    }

    @Override
    protected void requestData() {

    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//大于4.4
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                //4.4到5.0
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isrun = true;
    }

    @OnClick({R.id.save, R.id.get_code_btn, R.id.user_tel_clear, R.id.sms_code_clear, R.id.new_psw_clear, R.id.new_psw_again_clear})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_code_btn:
                getSmsCode();
                break;
            case R.id.save:
                savePsw();
                break;
            case R.id.user_tel_clear:
                mUserTel.setText("");
                break;
            case R.id.sms_code_clear:
                mSmsCode.setText("");
                break;
            case R.id.new_psw_clear:
                mNewPsw.setText("");
                break;
            case R.id.new_psw_again_clear:
                mNewPswAgain.setText("");
                break;
        }
    }

    private void addWatcherListener() {
        mUserTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearIcon(s, mUserTelClear);
                checkUserPost(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearIcon(s, mSmsCodeClear);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNewPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearIcon(s, mNewPswClear);
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
                showClearIcon(s, mNewPswAgainClear);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showClearIcon(CharSequence s, View view) {
        if(s.length() > 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void checkUserPost(int length) {
        if(length == 11) {
            mPhoneString = mUserTel.getText().toString().trim();
            mPresenter.getPersonalInfobyMobile(mPhoneString);
        } else {
            setHeaderHide();
        }
    }

    private void getSmsCode() {
        mPhoneString = mUserTel.getText().toString().trim();
        if(mPhoneString.isEmpty()) {
            ToastUtil.showS(getResources().getString(R.string.not_input_phone_number));
            return;
        }
        if(!CheckUtils.isPhoneNumber(mPhoneString)) {
            ToastUtil.showS(getResources().getString(R.string.not_match_phone_number));
            return;
        }
        mGetCode.setEnabled(false);
        showDialog();
        mPresenter.getSmsCode(mPhoneString);
    }

    private void savePsw() {
        mPhoneString = mUserTel.getText().toString().trim();
        if(mPhoneString.isEmpty()) {
            ToastUtil.showS(getResources().getString(R.string.not_input_phone_number));
            return;
        }
        if(!CheckUtils.isPhoneNumber(mPhoneString)) {
            ToastUtil.showS(getResources().getString(R.string.not_match_phone_number));
            return;
        }
        mSmsCodeString = mSmsCode.getText().toString().trim();
        if(mSmsCodeString.isEmpty()) {
            ToastUtil.showS(getResources().getString(R.string.not_input_sms_code));
            return;
        }
        passwd = mNewPsw.getText().toString().trim();
        rePasswd = mNewPswAgain.getText().toString().trim();
        if(!passwd.equals(rePasswd)) {
            ToastUtil.showS(getResources().getString(R.string.psw_put_different));
            return;
        }
        showDialog();
        mPresenter.forgetPsw(getForgetPswRequest());
    }

    @Override
    public void refreshPersonalInfo(EmployeeInfo info) {
        if (mLogo != null && mLogo.getVisibility() == View.VISIBLE) {
            mLogo.setVisibility(View.GONE);
        }
        if (mPersonalInfo != null && mPersonalInfo.getVisibility() == View.GONE) {
            mPersonalInfo.setVisibility(View.VISIBLE);
        }
//        mHeadImag.setImageURI("");
        mNickName.setText(info.getpName());
        mUerPost.setText(PersonalHeadUtil.getPost(info));
    }

    @Override
    public void setHeaderHide() {
        if(mLogo != null && mLogo.getVisibility() == View.GONE) {
            mLogo.setVisibility(View.VISIBLE);
        }
        if(mPersonalInfo != null && mPersonalInfo.getVisibility() == View.VISIBLE) {
            mPersonalInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void setForgetResetResult() {
        onBackPressed();
    }

    @Override
    public void setSmsCode() {
        mGetCode.post(timer);
    }

    @Override
    public void setSMSCodeError() {
        mGetCode.setEnabled(true);
    }

    Runnable timer = new Runnable() {
        @Override
        public void run() {
            if(isrun && mGetCode != null) {
                mGetCode.setText("剩余" + (time--) + "秒");
                if (time > 0) {
                    mGetCode.postDelayed(timer, 1000);
                    mGetCode.requestLayout();
                } else {
                    mGetCode.setEnabled(true);
                    mGetCode.setText("重新获取验证码");
                    time = 60;
                }
            }
        }
    };

    private Map<String, Object> getForgetPswRequest() {
        Map<String, Object> maps = new ArrayMap<>();
        maps.put("mobile", mPhoneString);
        maps.put("code", mSmsCodeString);
        maps.put("newPassword", passwd);
        return maps;
    }

    @Override
    protected void onDestroy() {
        isrun = false;
        super.onDestroy();
        LeakUtils.fixInputMethodManagerLeak(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
    }
}
