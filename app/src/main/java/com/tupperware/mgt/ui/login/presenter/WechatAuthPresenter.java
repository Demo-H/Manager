package com.tupperware.mgt.ui.login.presenter;

import com.dhunter.common.base.BasePresenter;
import com.dhunter.common.config.GlobalConfig;
import com.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.mgt.entity.login.WeChatRequst;
import com.tupperware.mgt.entity.login.WeChatResponse;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.contract.WechatAuthContract;
import com.tupperware.mgt.utils.ObjectUtil;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/11/29.
 */

public class WechatAuthPresenter extends BasePresenter implements WechatAuthContract.Presenter {

    private WechatAuthContract.View mView;
    private LoginDataManager mDataManager;


    @Inject
    public WechatAuthPresenter(LoginDataManager dataManager, WechatAuthContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }

    @Override
    public void WeChatAuth(String code) {
        mDataManager.setHeader(true);
        addDisposabe(mDataManager.WeChatAuth(new ErrorDisposableObserver<WeChatResponse>() {
            @Override
            public void onNext(WeChatResponse bean) {
                String json = ObjectUtil.jsonFormatter(bean.getModel());
                mView.hideDialog();
                if(bean.isSuccess()) {
                    if(bean.getExtra()  != null && bean.getExtra().isWxBind() && bean.getExtra().getToken() != null) {
                        String token = bean.getExtra().getToken().getToken();
                        mDataManager.saveSPData(GlobalConfig.LOGIN_TOKEN, token);
                        mDataManager.saveSPData(GlobalConfig.LOGIN_ACCOUNT, bean.getModel().getpUid());
//                        mDataManager.saveSPData(GlobalConfig.LOGIN_PSW, "");
                        mDataManager.saveSPData(GlobalConfig.LOGIN_EMPLOYEE_INFO, json);
                        mDataManager.setHeader(false);
                        mView.loginSuccess();
                    } else {
                        mView.setWeChatAuthSuccess(bean.getModel());
                    }
                } else {
                    mView.setWeChatAuthFail();
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setWeChatAuthFail();
            }

            @Override
            public void onComplete() {

            }
        }, getWeChatRequest(code)));
    }

    private WeChatRequst getWeChatRequest(String code) {
        WeChatRequst requestData = new WeChatRequst();
        requestData.setCode(code);
        requestData.setPlatform(GlobalConfig.PLATFORM);
        return requestData;
    }
}
