package com.dhunter.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.dhunter.common.R;
import com.dhunter.common.view.LoadingDialog;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by dhunter on 2018/6/22.
 */

public abstract class BaseRxFragment extends RxFragment implements FragmentUserVisibleController.UserVisibleCallback{

    private LoadingDialog loadingdialog;
    protected Activity mActivity;
    protected View parent;
    private boolean isInit; // 是否可以开始加载数据
    private boolean isCreated;
    private FragmentUserVisibleController userVisibleController;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public BaseRxFragment() {
        userVisibleController = new FragmentUserVisibleController(this, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInit = true;
        userVisibleController.activityCreated();
    }

    @Override
    public void onResume() {
        super.onResume();
        userVisibleController.resume();
        if (getUserVisibleHint()) {
            if (isInit && isCreated) {
                isInit = false;// 加载数据完成
                //System.out.println("应该去加载数据了");
                requestData();
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        userVisibleController.setUserVisibleHint(isVisibleToUser);
        // 每次切换fragment时调用的方法
        if (isVisibleToUser) {
            if (isInit&&isCreated) {
                isInit = false;//加载数据完成
                requestData();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        userVisibleController.pause();
    }

    /**
     * 初始化布局控件，拿到控件的引用，初始化ChildFragment 在ParentFragment 的布局
     */
    public abstract void initLayout();

    /**
     * 初始化后请求数据
     */
    public abstract void requestData();

    public abstract int getLayoutId();


    @Override
    public void setWaitingShowToUser(boolean waitingShowToUser) {
        userVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    @Override
    public boolean isWaitingShowToUser() {
        return userVisibleController.isWaitingShowToUser();
    }

    @Override
    public boolean isVisibleToUser() {
        return userVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {

    }

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
            loadingdialog = LoadingDialog.newInstance(getActivity().getResources().getString(R.string.loading));
            loadingdialog.show(getFragmentManager(), null);
        } catch (Exception e) {
            loadingdialog = null;
        }
    }

    public void toast(String msg) {
        if (getActivity() instanceof BaseRxActivity) {
            ((BaseRxActivity) getActivity()).toast(msg);
        } else {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

}
