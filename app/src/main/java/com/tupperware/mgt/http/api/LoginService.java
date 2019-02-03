package com.tupperware.mgt.http.api;

import com.tupperware.mgt.entity.BaseResponse;
import com.tupperware.mgt.entity.me.ChangePswRequest;
import com.tupperware.mgt.entity.datawindow.EmptyRequest;
import com.tupperware.mgt.entity.login.EmployeeResponse;
import com.tupperware.mgt.entity.login.LoginRequest;
import com.tupperware.mgt.entity.login.WeChatRequst;
import com.tupperware.mgt.entity.login.WeChatResponse;
import com.tupperware.mgt.entity.login.WechatBindMobileRequest;
import com.tupperware.mgt.entity.me.ImageCodeResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dhunter on 2018/7/5.
 */

public interface LoginService {

    /** 根据员工账号获取员工信息 **/
    @GET("backend/manage/manageLogin/getInfoById")
    Observable<EmployeeResponse> getPersonalInfobyJobId(@Query("employeeId") String staffId);

    /** 根据手机号获取员工信息 **/
    @GET("backend/manage/manageLogin/getInfoByMobile")
    Observable<EmployeeResponse> getPersonalInfobyMobile(@Query("mobile") String mobile);

    /** 账号登录 **/
    @POST("backend/manage/manageLogin/login")
    Observable<EmployeeResponse> loginByAccount(@Body LoginRequest requestData);

//    /** 重置密码 **/
//    @GET("backend/manage/manageLogin/updatePasswordByOldPsw")
//    Observable<BaseResponse> modifiedPsw(@QueryMap Map<String, Object> maps);


    /** 发送验证码短信 **/
    @GET("backend/manage/manageLogin/send")
    Observable<BaseResponse> getSmsCode(@Query("mobile") String mobile);

    /** 忘记密码 **/
    @GET("backend/manage/manageLogin/validateCodeAndChangePsw")
    Observable<BaseResponse> forgetPsw(@QueryMap Map<String, Object> maps);

    /** 手机验证码登录 **/
    @POST("backend/manage/manageLogin/validateCodeAndLogin")
    Observable<EmployeeResponse> LoginByMobile(@Body LoginRequest requestData);

    /** 微信授权登录 **/
    @POST("backend/manage/manageLogin/wxreg")
    Observable<WeChatResponse> WeChatAuth(@Body WeChatRequst requestData);

    /** 微信绑定手机号码  **/
    @POST("backend/manage/manageLogin/bindOpenIdAndLogin")
    Observable<EmployeeResponse> WeChatBindMobieLogin(@Body WechatBindMobileRequest requestData);

    /** 退出登录 **/
    @POST("backend/manage/manageLogin/logout")
    Observable<EmployeeResponse> logout(@Body EmptyRequest request);

    /** 根据员工账号、原密码、图片验证码修改密码 **/
    @POST("backend/manage/manageLogin/updatePasswordByOldPsw")
    Observable<EmployeeResponse> changePswByOldPsw(@Body ChangePswRequest request);


//    /** 获取图片验证码 **/
//    @Streaming
//    @GET("backend/manage/manageLogin/imagecode")
//    Observable<ResponseBody> getImageCode(@Url String fileUrl);

    /** 获取图片验证码 **/
    @GET("backend/manage/manageLogin/imagecode")
    Observable<ImageCodeResponse> getImageCode();

}
