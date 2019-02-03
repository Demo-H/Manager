package com.dhunter.common.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dhunter.common.R;
import com.dhunter.common.view.LoadingDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by dhunter on 2018/6/22.
 */

public abstract class BaseRxActivity extends RxAppCompatActivity {

    protected static final String TAG = BaseRxActivity.class.getSimpleName();
    private LoadingDialog loadingdialog;
    private Toast toast = null;
    private BaseRxActivity mActivity;

    public BaseRxActivity getActivity() {
        return mActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStateBarColor();
        mActivity = this;
//        setContentView(getLayoutId());
//        initLayout();
//        requestData();
    }

    public void setStateBarColor(){
        setStateBarColor(R.color.color_1fbbb9);
    }

    public void setStateBarColor(@ColorRes int color){
        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(color));
        }
    }


    protected  abstract int getLayoutId();

    protected abstract void initLayout();

    protected abstract void requestData();


    public void hideDialog() {
        if (loadingdialog != null) {
            try {
                loadingdialog.dismissAllowingStateLoss();
            } catch (Exception e) {
            }
            loadingdialog = null;
        }

    }

    public void showDialog() {
        hideDialog();
        try { //防止在Activity 还没有恢复状态就showDialog
            loadingdialog = LoadingDialog.newInstance(getResources().getString(R.string.loading));
            loadingdialog.show(getSupportFragmentManager(), null);
        } catch (Exception e) {
            loadingdialog = null;
        }
    }

    /**
     * 防止一直重复的提醒了
     */
    public void toast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
