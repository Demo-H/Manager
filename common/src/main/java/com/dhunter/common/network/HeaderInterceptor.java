package com.dhunter.common.network;

import android.content.Context;
import android.util.Log;

import com.dhunter.common.config.GlobalConfig;
import com.dhunter.common.utils.NetworkUtils;

import java.io.IOException;
import java.util.Set;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dhunter on 2018/6/25.
 */

public class HeaderInterceptor<T> implements Interceptor {

    private Context context;
//    private Map<String, T> headers;

    public HeaderInterceptor(Context context) {
//        this.headers = headers;
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isNetworkAvailable(context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                    .build();
        } else {
            Log.d("http_request", "intercept: request header="+request.url() + "/\r body="+request.body() );
            Request.Builder builder = request.newBuilder();
            if (GlobalConfig.headers != null && GlobalConfig.headers.size() > 0) {
                Set<String> keys = GlobalConfig.headers.keySet();
                for (String headerKey : keys) {
                    builder.addHeader(headerKey, GlobalConfig.headers.get(headerKey) == null? "": (String)GlobalConfig.headers.get(headerKey)).build();
                }
            }
            request = builder.build();
        }
        Response response = chain.proceed(request);
        if (NetworkUtils.isNetworkAvailable(context)) {
            int maxAge = 10 * 60; // 有网络时 设置缓存超时时间10分钟
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 7; // 无网络时，设置超时为1周
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
