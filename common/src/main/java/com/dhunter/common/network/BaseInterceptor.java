package com.dhunter.common.network;

import android.content.Context;
import android.util.Log;

import com.dhunter.common.config.GlobalConfig;
import com.dhunter.common.utils.NetworkUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dhunter on 2018/6/25.
 */

public class BaseInterceptor<T> implements Interceptor {
    private Context context;
    private Map<String, T> headers;

    public BaseInterceptor(Map<String, T> headers, Context context) {
        this.context = context;
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(!NetworkUtils.isNetworkAvailable(context)){
            throw new NetWorkCodeException();
        }
        Request request = chain.request();
        if(GlobalConfig.DEBUG){
            Log.d("http_request", "intercept: request header="+request.url() + "/\r body="+request.body() );
        }
        Request.Builder builder = request.newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey) == null? "": (String)headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());

    }
}