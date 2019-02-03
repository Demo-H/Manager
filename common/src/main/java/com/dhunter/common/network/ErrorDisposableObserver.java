package com.dhunter.common.network;

import android.util.Log;
import android.widget.Toast;

import com.dhunter.common.GlobalAppComponent;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by dhunter on 2018/6/25.
 */

public abstract class ErrorDisposableObserver<T> extends DisposableObserver<T> {
    @Override
    public void onError(Throwable e) {
        //处理结果为false的异常
        if (e instanceof ResultException){
            int code = ((ResultException)e).getErrorCode();
            String message = ((ResultException)e).getMessage();
            Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), "Code = " + code + ", " + message, Toast.LENGTH_SHORT).show();
        } else{
            //此处可按状态码解析或error类型，进行分别处理其他error, getResponseThrowable(e)
            NetWorkCodeException.ResponseThrowable responseThrowable = NetWorkCodeException.getResponseThrowable(e);
            Toast.makeText(GlobalAppComponent.getAppComponent().getContext(), "Code = " + responseThrowable.code + ", " + responseThrowable.message, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onNext(T t){
    }

    @Override
    public void onComplete() {
    }
}