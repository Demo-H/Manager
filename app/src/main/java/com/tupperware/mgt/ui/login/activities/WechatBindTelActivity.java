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

import com.dhunter.common.utils.LeakUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.config.Constant;
import com.tupperware.mgt.entity.login.EmployeeInfo;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.main.MainActivity;
import com.tupperware.mgt.ui.login.component.DaggerWechatBindTelActivityComponent;
import com.tupperware.mgt.ui.login.contract.WechatBindTelContract;
import com.tupperware.mgt.ui.login.module.WechatBindTelPresenterModule;
import com.tupperware.mgt.ui.login.presenter.WechatBindTelPresenter;
import com.tupperware.mgt.utils.ActivityManager;
import com.tupperware.mgt.utils.CheckUtils;
import com.tupperware.mgt.utils.PersonalHeadUtil;
import com.tupperware.mgt.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/19.
 */

public class WechatBindTelActivity extends BaseActivity implements WechatBindTelContract.View {

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
    @BindView(R.id.nick)
    TextView mWeChatNick;
    @BindView(R.id.user_tel)
    EditText mUserTel;
    @BindView(R.id.sms_code)
    EditText mSmsCode;
    @BindView(R.id.get_code_btn)
    Button mGetCode;
    @BindView(R.id.other_login)
    TextView mOtherLogin;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.user_tel_clear)
    ImageView mUserTelClear;
    @BindView(R.id.sms_code_clear)
    ImageView mSmsCodeClear;

    private String mobile;
    private String smsCode;
    private int time = 60;
    private boolean isrun = false; //标识短验控件线程销毁
    private EmployeeInfo fullPersonalInfo;

    @Inject
    WechatBindTelPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wechat_bind_tel;
    }

    @Override
    protected void initLayout() {
        DaggerWechatBindTelActivityComponent.builder()
                .appComponent(getAppComponent())
                .wechatBindTelPresenterModule(new WechatBindTelPresenterModule(this, LoginDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        ActivityManager.getInstance().addActivity(this);
        fullPersonalInfo = (EmployeeInfo) getIntent().getSerializableExtra(Constant.WECHAT_INFO);
        showWeChatInfo();
        addWatcherListener();
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        isrun = true;
    }

    @Override
    protected void onDestroy() {
        isrun = false;
        super.onDestroy();
        LeakUtils.fixInputMethodManagerLeak(this);
    }

    @OnClick({R.id.login, R.id.get_code_btn, R.id.other_login, R.id.user_tel_clear, R.id.sms_code_clear})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_code_btn:
                getSmsCode();
                break;
            case R.id.login:
                weChatBindMobile();
                break;
            case R.id.other_login:
                Intent intent = new Intent(WechatBindTelActivity.this, LoginSelectActivity.class);
                startActivity(intent);
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.user_tel_clear:
                mUserTel.setText("");
                break;
            case R.id.sms_code_clear:
                mSmsCode.setText("");
                break;
        }
    }

    private void addWatcherListener(){
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
            mobile = mUserTel.getText().toString().trim();
            mPresenter.getPersonalInfobyMobile(mobile);
        } else {
            showWeChatInfo();
            mUerPost.setText("");
        }
    }

    private void weChatBindMobile() {
        mobile = mUserTel.getText().toString().trim();
        if(mobile.isEmpty()) {
            toast(getResources().getString(R.string.not_input_phone_number));
            return;
        }
        smsCode = mSmsCode.getText().toString().trim();
        if(smsCode.isEmpty()) {
            toast(getResources().getString(R.string.not_input_sms_code));
            return;
        }
        showDialog();
        mPresenter.wechatBindMobile(mobile, smsCode, fullPersonalInfo);
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
    public void showWeChatInfo() {
        if(fullPersonalInfo != null) {
            if (mLogo != null && mLogo.getVisibility() == View.VISIBLE) {
                mLogo.setVisibility(View.GONE);
            }
            if (mPersonalInfo != null && mPersonalInfo.getVisibility() == View.GONE) {
                mPersonalInfo.setVisibility(View.VISIBLE);
            }
            mNickName.setText(fullPersonalInfo.getpWxNickname());
            mWeChatNick.setText(fullPersonalInfo.getpWxNickname());
            mHeadImag.setImageURI(fullPersonalInfo.getpWxHeadimg());
        } else {
            setHeaderHide();
        }
    }

    private void getSmsCode() {
        mobile = mUserTel.getText().toString().trim();
        if(mobile.isEmpty()) {
            ToastUtil.showS(getResources().getString(R.string.not_input_phone_number));
            return;
        }
        if(!CheckUtils.isPhoneNumber(mobile)) {
            ToastUtil.showS(getResources().getString(R.string.not_match_phone_number));
            return;
        }
        mGetCode.setEnabled(false);
        showDialog();
        mPresenter.getSmsCode(mobile);
    }

    public void setHeaderHide() {
        if(mLogo != null && mLogo.getVisibility() == View.GONE) {
            mLogo.setVisibility(View.VISIBLE);
        }
        if(mPersonalInfo != null && mPersonalInfo.getVisibility() == View.VISIBLE) {
            mPersonalInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSmsCode() {
        mGetCode.post(timer);
    }

    @Override
    public void setSMSCodeError() {
        mGetCode.setEnabled(true);
    }

    @Override
    public void bindResult() {
        Intent intent = new Intent(WechatBindTelActivity.this, MainActivity.class);
        startActivity(intent);
        ActivityManager.getInstance().exit();
    }

    Runnable timer = new Runnable() {
        @Override
        public void run() {
            if(isrun) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
    }
}
