package com.tupperware.mgt.ui.login.presenter;

import android.support.v4.util.ArrayMap;

import com.dhunter.common.base.BasePresenter;
import com.dhunter.common.config.GlobalConfig;
import com.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.mgt.entity.BaseResponse;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.ResetPasswordContract;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/11/26.
 */

public class ResetPasswordPresenter extends BasePresenter implements ResetPasswordContract.Presenter {

    private ResetPasswordContract.View mView;
    private LoginDataManager mDataManager;


    @Inject
    public ResetPasswordPresenter(LoginDataManager dataManager, ResetPasswordContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }

    @Override
    public void modifiedPsw(final String newPswString) {
//        addDisposabe(mDataManager.modifiedPsw(new ErrorDisposableObserver<BaseResponse>() {
//            @Override
//            public void onNext(BaseResponse bean) {
//                mView.hideDialog();
//                if(bean.isSuccess()) {
//                    mView.setResetSuccess();
//                } else {
//                    if(bean.getMessage() != null && !bean.getMessage().isEmpty()) {
//                        mView.toast(bean.getMessage());
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                mView.hideDialog();
//                mView.toast("网络异常，请重试");
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        }, getModifiedRequest(newPswString)));
    }

//    private Map<String, Object> getModifiedRequest(String newPswString) {
//        String employeeId = mDataManager.getSPData(GlobalConfig.LOGIN_ACCOUNT);
//        Map<String, Object> maps = new ArrayMap<>();
//        maps.put("employeeId", employeeId);
//        maps.put("oldPassword", oldPsw);
//        maps.put("newPassword", newPswString);
//        return maps;
//    }
}
