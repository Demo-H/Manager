package com.dhunter.common.refreshlayout.listener;

import android.support.annotation.NonNull;

import com.dhunter.common.refreshlayout.api.RefreshLayout;


/**
 * 刷新监听器
 * Created by https://github.com/scwang90/SmartRefreshLayout on 2017/5/26.
 */

public interface OnRefreshListener {
    void onRefresh(@NonNull RefreshLayout refreshLayout);
}
