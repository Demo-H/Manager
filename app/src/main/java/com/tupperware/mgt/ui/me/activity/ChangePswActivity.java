package com.tupperware.mgt.ui.me.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhunter.common.utils.FileUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.me.component.DaggerChangePswActivityComponent;
import com.tupperware.mgt.ui.me.component.DaggerSettingActivityComponent;
import com.tupperware.mgt.ui.me.contract.SettingContract;
import com.tupperware.mgt.ui.me.module.SettingPresenterModule;
import com.tupperware.mgt.ui.me.presenter.SettingPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePswActivity extends BaseActivity implements SettingContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.old_psw_et)
    EditText oldPswEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.new_psw)
    EditText newPswEt;
    @BindView(R.id.new_psw_again)
    EditText newPswConfirmEt;


    @BindView(R.id.code_img)
    SimpleDraweeView codeImg;


    String oldPsw;
    String newPsw;
    String newPswConfirm;
    String code;

    @Inject
    SettingPresenter settingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_psw;
    }

    @Override
    protected void initLayout() {
        toolbarTitle.setText("修改密码");

        DaggerChangePswActivityComponent.builder()
                .appComponent(getAppComponent())
                .settingPresenterModule(new SettingPresenterModule(this, LoginDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
    }

    @Override
    protected void requestData() {
        settingPresenter.getImageCode();
    }

    @Override
    public void exitSuccess() {
    }

    @Override
    public void changePswSuccess() {
        toast("密码修改成功！");
        finish();
    }

    @Override
    public void updateImageCode(final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                codeImg.setImageBitmap(bitmap);
            }
        });

    }

    /**
     * 确认修改
     */
    private void saveChange(){
        oldPsw = oldPswEt.getText().toString().trim();
        newPsw = newPswEt.getText().toString().trim();
        newPswConfirm = newPswConfirmEt.getText().toString().trim();
        code = codeEt.getText().toString().trim();
        if (TextUtils.isEmpty(oldPswEt.getText().toString().trim())){
            toast("输入的旧密码为空！");
            return;
        }
        if (TextUtils.isEmpty(codeEt.getText().toString().trim())){
            toast("输入的验证码为空！");
            return;
        }
        if (TextUtils.isEmpty(newPswEt.getText().toString().trim())){
            toast("输入的新密码为空！");
            return;
        }
        if (!newPsw.equals(newPswConfirm)){
            toast("两次输入的密码不一致！");
            return;
        }
        showDialog();
        settingPresenter.changePswAction(oldPsw,newPsw,code);
    }


    @OnClick({R.id.code_img,R.id.save,R.id.left_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.code_img:
                settingPresenter.getImageCode();
                break;
            case R.id.save:
                saveChange();
                break;
            case R.id.left_back:
                finish();
                break;
        }
    }
}
