package com.tupperware.mgt.ui.login.presenter;

import com.dhunter.common.base.BasePresenter;
import com.dhunter.common.config.GlobalConfig;
import com.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.mgt.entity.datawindow.KpiYMResponse;
import com.tupperware.mgt.entity.login.EmployeeResponse;
import com.tupperware.mgt.entity.login.LoginRequest;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.LoginContract;
import com.tupperware.mgt.utils.ObjectUtil;
import com.tupperware.mgt.utils.ToastUtil;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/11/26.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private LoginDataManager mDataManager;


    @Inject
    public LoginPresenter(LoginDataManager dataManager, LoginContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }

    @Override
    public void loginByAccount(final String employeeId, final String psw,final boolean isAuto) {
        mDataManager.setHeader(true);
        addDisposabe(mDataManager.loginByAccount(new ErrorDisposableObserver<EmployeeResponse>() {
            @Override
            public void onNext(EmployeeResponse bean) {
                String json = ObjectUtil.jsonFormatter(bean.getModel());
                mView.hideDialog();
                if(bean.isSuccess() && bean.getExtra() != null) {
                    String token = bean.getExtra().getToken();
                    mDataManager.saveSPData(GlobalConfig.LOGIN_TOKEN, token);
                    mDataManager.saveSPData(GlobalConfig.LOGIN_ACCOUNT, employeeId);
                    mDataManager.saveSPData(GlobalConfig.LOGIN_EMPLOYEE_INFO, json);
                    mDataManager.setHeader(false);
                    mView.setLoginResult();
                } else {
                    if(bean.getMessage() != null && !bean.getMessage().isEmpty()) {
                        ToastUtil.showSelfS(bean.getMessage());
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
        }, getLoginRequest(employeeId, psw,isAuto)));
    }

    @Override
    public void getPersonalInfobyJobId(String employeeId) {
        mDataManager.setHeader(true);
        addDisposabe(mDataManager.getPersonalInfobyJobId(new ErrorDisposableObserver<EmployeeResponse>() {
            @Override
            public void onNext(EmployeeResponse bean) {
//                String json = ObjectUtil.jsonFormatter(bean);
                if(bean.isSuccess() && bean.getModel() != null) {
                    mView.refreshPersonalInfo(bean.getModel());
                } else {
//                    if(bean.getMessage() != null && !bean.getMessage().isEmpty()) {
//                        mView.toast(bean.getMessage());
//                    }
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
        }, employeeId));
    }

    private LoginRequest getLoginRequest(String employeeId, String psw,boolean isAuto) {
        LoginRequest requestData = new LoginRequest();
        requestData.setEmployeeId(employeeId);
        requestData.setPassword(psw);
        requestData.setPlatform(GlobalConfig.PLATFORM);
        if (isAuto){
            requestData.setIsAuto("1");
        }else {
            requestData.setIsAuto("0");
        }
        return requestData;
    }

}
