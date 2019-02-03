package com.dhunter.common.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by umt041 on 2019/1/18.
 */
public class RequestFailedInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Response response = chain.proceed(oldRequest);
        byte[] respBytes = response.body()
                .bytes();
        String respString = new String(respBytes);
        try {
            JSONObject object = new JSONObject(respString);
            boolean isSuccess = object.optBoolean("success");
            int code = object.optInt("resultCode");
            String message = object.getString("message");
            if (!isSuccess) {
                throw new ResultException(message, code);//抛出自定义异常,在subscriber的onError中被接收,达到分离处理的目的
            }
            return response.newBuilder()
                    .body(ResponseBody.create(null, respBytes))
                    .build();//在前面获取bytes的时候response的stream已经被关闭了,要重新生成response
        } catch (JSONException e) {
            e.printStackTrace();
            throw new ResultException("解析异常",-1);
        }
    }
}
