package com.dhunter.common.refreshlayout.listener;

import android.support.annotation.NonNull;

import com.dhunter.common.refreshlayout.api.RefreshLayout;


/**
 * 加载更多监听器
 * Created by https://github.com/scwang90/SmartRefreshLayout on 2017/5/26.
 */

public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}
