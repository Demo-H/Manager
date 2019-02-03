package com.tupperware.mgt.ui.login.presenter;

import com.dhunter.common.base.BasePresenter;
import com.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.mgt.entity.BaseResponse;
import com.tupperware.mgt.entity.login.EmployeeResponse;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.ForgetPswContract;
import com.tupperware.mgt.utils.StringUtil;
import com.tupperware.mgt.utils.ToastUtil;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/11/26.
 */

public class ForgetPswPresenter extends BasePresenter implements ForgetPswContract.Presenter {

    private ForgetPswContract.View mView;
    private LoginDataManager mDataManager;


    @Inject
    public ForgetPswPresenter(LoginDataManager dataManager, ForgetPswContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }

    @Override
    public void forgetPsw(Map<String, Object> maps) {
        mDataManager.setHeader(true);
        addDisposabe(mDataManager.forgetPsw(new ErrorDisposableObserver<BaseResponse>() {
            @Override
            public void onNext(BaseResponse bean) {
                mView.hideDialog();
                if(bean.isSuccess()) {
                    mView.setForgetResetResult();
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
        }, maps));
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
                    if(bean.getMessage() != null && !StringUtil.isEmpty(bean.getMessage())) {
                        ToastUtil.showSelfS(bean.getMessage());
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
}
