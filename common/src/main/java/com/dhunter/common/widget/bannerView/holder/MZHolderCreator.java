package com.dhunter.common.widget.bannerView.holder;

/**
 * Created by dhunter on 2018/6/27.
 */

public interface MZHolderCreator<V extends MZViewHolder> {

    /**
     * 创建ViewHolder
     * @return
     */
    V createViewHolder();
}
