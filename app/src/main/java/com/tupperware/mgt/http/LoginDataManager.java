package com.tupperware.mgt.http;


import com.dhunter.common.network.DataManager;
import com.tupperware.mgt.entity.BaseResponse;
import com.tupperware.mgt.entity.me.ChangePswRequest;
import com.tupperware.mgt.entity.datawindow.EmptyRequest;
import com.tupperware.mgt.entity.login.EmployeeResponse;
import com.tupperware.mgt.entity.login.LoginRequest;
import com.tupperware.mgt.entity.login.WeChatRequst;
import com.tupperware.mgt.entity.login.WeChatResponse;
import com.tupperware.mgt.entity.login.WechatBindMobileRequest;
import com.tupperware.mgt.entity.me.ImageCodeResponse;
import com.tupperware.mgt.http.api.LoginService;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by dhunter on 2018/7/5.
 * 网络请求不需要token头参数的接口放于该类
 */

public class LoginDataManager extends BaseDataManager {

    public LoginDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static LoginDataManager getInstance(DataManager dataManager){
        return new LoginDataManager(dataManager);
    }

    /** 根据员工账号获取员工信息 **/
    public Disposable getPersonalInfobyJobId(DisposableObserver<EmployeeResponse> consumer, String staffId) {
        Observable observable = getService(LoginService.class).getPersonalInfobyJobId(staffId);
        return changeIOToMainThread(observable, consumer);
    }

    /** 根据手机号获取员工信息 **/
    public Disposable getPersonalInfobyMobile(DisposableObserver<EmployeeResponse> consumer,String mobile) {
        Observable observable = getService(LoginService.class).getPersonalInfobyMobile(mobile);
        return changeIOToMainThread(observable, consumer);
    }

    /** 账号登录 **/
    public Disposable loginByAccount(DisposableObserver<EmployeeResponse> consumer, LoginRequest requestData) {
        Observable observable = getService(LoginService.class).loginByAccount(requestData);
        return changeIOToMainThread(observable, consumer);
    }

    /** 发送验证码短信 **/
    public Disposable getSmsCode(DisposableObserver<BaseResponse> consumer, String mobile) {
        Observable observable = getService(LoginService.class).getSmsCode(mobile);
        return changeIOToMainThread(observable, consumer);
    }

//    /** 修改密码 **/
//    public Disposable modifiedPsw(DisposableObserver<BaseResponse> consumer, Map<String, Object> maps) {
//        Observable observable = getService(LoginService.class).modifiedPsw(maps);
//        return changeIOToMainThread(observable, consumer);
//    }

    /** 忘记密码 **/
    public Disposable forgetPsw(DisposableObserver<BaseResponse> consumer, Map<String, Object> maps) {
        Observable observable = getService(LoginService.class).forgetPsw(maps);
        return changeIOToMainThread(observable, consumer);
    }

    /** 手机验证码登录 **/
    public Disposable LoginByMobile(DisposableObserver<EmployeeResponse> consumer, LoginRequest requestData) {
        Observable observable = getService(LoginService.class).LoginByMobile(requestData);
        return changeIOToMainThread(observable, consumer);
    }

    /** 微信授权登录 **/
    public Disposable WeChatAuth(DisposableObserver<WeChatResponse> consumer, WeChatRequst requestData) {
        Observable observable = getService(LoginService.class).WeChatAuth(requestData);
        return changeIOToMainThread(observable, consumer);
    }

    /** 微信绑定手机号码 **/
    public Disposable WeChatBindMobieLogin(DisposableObserver<EmployeeResponse> consumer, WechatBindMobileRequest requestData) {
        Observable observable = getService(LoginService.class).WeChatBindMobieLogin(requestData);
        return changeIOToMainThread(observable, consumer);
    }

    /** 登出系统 **/
    public Disposable logoutApp(DisposableObserver<EmployeeResponse> consumer, EmptyRequest requestData) {
        Observable observable = getService(LoginService.class).logout(requestData);
        return changeIOToMainThread(observable, consumer);
    }

    /** 根据旧密码修改密码 **/
    public Disposable changePswByOldPsw(DisposableObserver<EmployeeResponse> consumer, ChangePswRequest requestData) {
        Observable observable = getService(LoginService.class).changePswByOldPsw(requestData);
        return changeIOToMainThread(observable, consumer);
    }

    /** 获取图片验证码 **/
    public Disposable getImageCode(DisposableObserver<ImageCodeResponse> consumer) {
        Observable observable = getService(LoginService.class).getImageCode();
        return changeIOToMainThread(observable, consumer);
    }

}
