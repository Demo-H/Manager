package com.dhunter.common.widget.bannerView.holder;

import android.content.Context;
import android.view.View;

/**
 * Created by dhunter on 2018/6/27.
 */

public interface MZViewHolder<T> {
    /**
     * 创建View
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 绑定数据
     * @param context
     * @param postion
     * @param data
     */
    void onBind(Context context, int postion, T data);
}
