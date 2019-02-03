package com.dhunter.common.refreshlayout.api;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 默认Header创建器
 * Created by https://github.com/scwang90/SmartRefreshLayout on 2018/1/26.
 */
public interface DefaultRefreshHeaderCreator {
    @NonNull
    RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout);
}
