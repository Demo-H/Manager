package com.tupperware.mgt.ui.login.presenter;

import com.dhunter.common.base.BasePresenter;
import com.dhunter.common.config.GlobalConfig;
import com.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.mgt.entity.BaseResponse;
import com.tupperware.mgt.entity.login.EmployeeInfo;
import com.tupperware.mgt.entity.login.EmployeeResponse;
import com.tupperware.mgt.entity.login.WechatBindMobileRequest;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.WechatBindTelContract;
import com.tupperware.mgt.utils.ObjectUtil;
import com.tupperware.mgt.utils.ToastUtil;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/11/27.
 */

public class WechatBindTelPresenter extends BasePresenter implements WechatBindTelContract.Presenter {

    private WechatBindTelContract.View mView;
    private LoginDataManager mDataManager;


    @Inject
    public WechatBindTelPresenter(LoginDataManager dataManager, WechatBindTelContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }

    @Override
    public void wechatBindMobile(String mobile, String smsCode, EmployeeInfo fullPersonalInfo) {
        mDataManager.setHeader(true);
        addDisposabe(mDataManager.WeChatBindMobieLogin(new ErrorDisposableObserver<EmployeeResponse>() {
            @Override
            public void onNext(EmployeeResponse bean) {
                String json = ObjectUtil.jsonFormatter(bean.getModel());
                mView.hideDialog();
                if(bean.isSuccess() && bean.getExtra() != null) {
                    String token = bean.getExtra().getToken();
                    mDataManager.saveSPData(GlobalConfig.LOGIN_TOKEN, token);
                    mDataManager.saveSPData(GlobalConfig.LOGIN_EMPLOYEE_INFO, json);
                    mDataManager.setHeader(false);
                    mView.bindResult();
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
        }, getWechatBindMobileRequest(smsCode, mobile, fullPersonalInfo)));
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
                        ToastUtil.showSelfS(bean.getMessage());
                    }
                    mView.showWeChatInfo();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.showWeChatInfo();
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


    private WechatBindMobileRequest getWechatBindMobileRequest(String code, String mobile, EmployeeInfo fullPersonalInfo) {
        WechatBindMobileRequest requestData = new WechatBindMobileRequest();
        requestData.setCode(code);
        requestData.setMobile(mobile);
        requestData.setFmsEmployeeWx(fullPersonalInfo);
        requestData.setPlatform(GlobalConfig.PLATFORM);
        String json = ObjectUtil.jsonFormatter(requestData);
        return requestData;
    }
}
