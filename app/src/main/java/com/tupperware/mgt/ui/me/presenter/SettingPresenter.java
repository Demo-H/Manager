package com.tupperware.mgt.ui.me.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.dhunter.common.base.BasePresenter;
import com.dhunter.common.config.GlobalConfig;
import com.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.mgt.entity.me.ChangePswRequest;
import com.tupperware.mgt.entity.datawindow.EmptyRequest;
import com.tupperware.mgt.entity.login.EmployeeResponse;
import com.tupperware.mgt.entity.me.ImageCodeResponse;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.me.contract.SettingContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/11/26.
 */

public class SettingPresenter extends BasePresenter implements SettingContract.Presenter {

    private SettingContract.View mView;
    private LoginDataManager mDataManager;


    @Inject
    public SettingPresenter(LoginDataManager dataManager, SettingContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }


    /**
     * 退出登录
     */
    @Override
    public void exitApp() {
        mDataManager.logoutApp(new ErrorDisposableObserver<EmployeeResponse>() {
            @Override
            public void onNext(EmployeeResponse bean) {
                if(bean.isSuccess()) {
                    mView.exitSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, new EmptyRequest());
    }

    /**
     * 修改密码
     * @param psw
     * @param newPsw
     * @param code
     */
    @Override
    public void changePswAction(final String psw,final String newPsw,final String code) {
        mDataManager.changePswByOldPsw(new ErrorDisposableObserver<EmployeeResponse>() {
            @Override
            public void onNext(EmployeeResponse bean) {
                if(bean.isSuccess()) {
                    mView.changePswSuccess();
                } else {
                    mView.toast(bean.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
            }

            @Override
            public void onComplete() {
                mView.hideDialog();
            }
        }, getChangePswRequest(psw,newPsw,code));
    }

    /**
     * 获取图片验证码
     */
    @Override
    public void getImageCode() {
        mDataManager.getImageCode(new ErrorDisposableObserver<ImageCodeResponse>() {
            @Override
            public void onNext(final ImageCodeResponse imageCodeResponse) {
                String  bytes = imageCodeResponse.getExtra();
                try {
                    byte[] bitmapArray;
                    bitmapArray = Base64.decode(bytes, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
                    mView.updateImageCode(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private ChangePswRequest getChangePswRequest(String psw,String newPsw,String code) {
        ChangePswRequest requestData = new ChangePswRequest();
        requestData.setEmployeeId(mDataManager.getSPData(GlobalConfig.LOGIN_ACCOUNT));
        requestData.setPassword(psw);
        requestData.setNewPassword(newPsw);
        requestData.setImageCode(code);
        return requestData;
    }
}
