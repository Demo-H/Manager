package com.dhunter.common.network;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import okhttp3.Connection;
import retrofit2.HttpException;

/**
 * Created by dhunter on 2018/6/25.
 */

public class NetWorkCodeException extends RuntimeException {

    /**
     * ========================返回的code==================================
     */
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    /**
     * 自定义的code
     */

    //未知错误
    public static final int UNKNOWN = 1000;
    //解析错误
    public static final int PARSE_ERROR = 1001;
    //网络错误
    public static final int NETWORD_ERROR = 1002;
    //协议出错
    public static final int HTTP_ERROR = 1003;
    //证书出错
    public static final int SSL_ERROR = 1005;

    public static ResponseThrowable getResponseThrowable(Throwable e) {
        ResponseThrowable ex;

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable();
            ex.code = httpException.code();
            switch (httpException.code()) {
                case BAD_REQUEST:
                    ex.message = "请检查请求内容";
                    break;
                case UNAUTHORIZED:
                    ex.message = "认证失败";
                    break;
                case FORBIDDEN:
//                    ex.code = HTTP_ERROR;
                    ex.message = "请检查访问权限";
                    break;
                case NOT_FOUND:
                    ex.message = "Request Not Found";
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = "请求超时";
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.message = "服务器出错";
                    break;
                case BAD_GATEWAY:
                    ex.message = "防火墙";
                case SERVICE_UNAVAILABLE:
                    ex.message = "服务器负载";
                case GATEWAY_TIMEOUT:
                    ex.message = "访问超时";
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable();
            ex.code = resultException.code;
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable();
            ex.code = PARSE_ERROR;
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof Connection) {
            ex = new ResponseThrowable();
            ex.code = NETWORD_ERROR;
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable();
            ex.code = SSL_ERROR;
            ex.message = "证书验证失败";
            return ex;
        } else {
            ex = new ResponseThrowable();
            ex.code = NETWORD_ERROR;
            ex.message = "网络错误";
            return ex;
        }
    }


    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;
    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}
