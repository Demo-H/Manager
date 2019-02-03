package com.tupperware.mgt.ui.login.presenter;

import com.dhunter.common.base.BasePresenter;
import com.dhunter.common.config.GlobalConfig;
import com.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.mgt.entity.BaseResponse;
import com.tupperware.mgt.entity.login.EmployeeResponse;
import com.tupperware.mgt.entity.login.LoginRequest;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.SmsCodeLoginContract;
import com.tupperware.mgt.utils.ObjectUtil;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/11/27.
 */

public class SmsCodeLoginPresenter extends BasePresenter implements SmsCodeLoginContract.Presenter {
    private SmsCodeLoginContract.View mView;
    private LoginDataManager mDataManager;


    @Inject
    public SmsCodeLoginPresenter(LoginDataManager dataManager, SmsCodeLoginContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }

    @Override
    public void LoginByMobile(final String mobile, String smsCode) {
        mDataManager.setHeader(true);
        addDisposabe(mDataManager.LoginByMobile(new ErrorDisposableObserver<EmployeeResponse>() {
            @Override
            public void onNext(EmployeeResponse bean) {
                String json = ObjectUtil.jsonFormatter(bean.getModel());
                mView.hideDialog();
                if(bean.isSuccess() && bean.getExtra() != null) {
                    String token = bean.getExtra().getToken();
                    mDataManager.saveSPData(GlobalConfig.LOGIN_TOKEN, token);
                    mDataManager.saveSPData(GlobalConfig.LOGIN_MOBILE_PHONE, mobile);
                    mDataManager.saveSPData(GlobalConfig.LOGIN_EMPLOYEE_INFO, json);
                    mDataManager.setHeader(false);
                    mView.setLoginResult();
                } else {
                    if(bean.getMessage() != null && !bean.getMessage().isEmpty()) {
                        mView.toast(bean.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
            }

            @Override
            public void onComplete() {

            }
        }, getLoginByMobileRequest(mobile, smsCode)));
    }

    @Override
    public void getPersonalInfobyMobile(String mobile) {
        mDataManager.setHeader(true);
        addDisposabe(mDataManager.getPersonalInfobyMobile(new ErrorDisposableObserver<EmployeeResponse>() {
            @Override
            public void onNext(EmployeeResponse bean) {
                if(bean.isSuccess() && bean.getModel() != null) {
                    mView.refreshPersonalInfo(bean.getModel());
                } else {
                    if(bean.getMessage() != null && !bean.getMessage().isEmpty()) {
                        mView.toast(bean.getMessage());
                    }
                    mView.setHeaderHide();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.setHeaderHide();
            }

            @Override
            public void onComplete() {

            }
        }, mobile));
    }

    @Override
    public void getSmsCode(String mobile) {
        mDataManager.setHeader(true);
        addDisposabe(mDataManager.getSmsCode(new ErrorDisposableObserver<BaseResponse>() {
            @Override
            public void onNext(BaseResponse bean) {
                mView.hideDialog();
                if(bean.isSuccess()) {
                    mView.setSmsCode();
                } else {
                    if(bean.getMessage() != null && !bean.getMessage().isEmpty()) {
                        mView.toast(bean.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setSMSCodeError();
            }


            @Override
            public void onComplete() {

            }
        }, mobile));
    }

    private LoginRequest getLoginByMobileRequest(String mobile, String smsCode) {
        LoginRequest requestData = new LoginRequest();
        requestData.setPlatform(GlobalConfig.PLATFORM);
        requestData.setMobile(mobile);
        requestData.setCode(smsCode);
        return requestData;
    }
}
