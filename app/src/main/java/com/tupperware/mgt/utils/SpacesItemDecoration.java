package com.tupperware.mgt.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tupperware.mgt.config.Constant;


/**
 * Created by dhunter on 2018/3/9.
 * 用来自定义recycleView空隙
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int item;
    private int position;
    private int itemCount; //最后一条的postion

    public SpacesItemDecoration(int space, int item) {
        this.space = space;
        this.item = item;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        position = parent.getChildLayoutPosition(view);

        if(item == Constant.TYPE_GOODS_LIST_VIEW) { //第一行顶部需要分割线
            if(position == 0 || position == 1) {
                outRect.top = space * 3;
            } else {
                outRect.top = 0;
            }
            if(position % 2 == 0) {
                outRect.left = space * 3;
                outRect.right = space;
            } else {
                outRect.left = space;
                outRect.right = space * 3;
            }
            outRect.bottom = space * 3;
        } else if(item == Constant.TYPE_ORDER_LIST_VIEW) {
            outRect.top = 0;
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = space;
        } else if(item == Constant.TYPE_SECOND_KILL_LIST_VIEW) {
            outRect.top = 0;
            if(position % 2 == 0) {
                outRect.left = space * 3;
                outRect.right = space;
            } else {
                outRect.left = space;
                outRect.right = space * 3;
            }
            outRect.bottom = space * 3;
        } else if (item == Constant.TYPE_FLOW_SPEC_ITEM) {
            outRect.top = space;
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
        } else if (item == Constant.TYPE_SECOND_LEVEL_ITEM) {
            if(position == 0) {
                outRect.top = 0;
                outRect.bottom = space;
                outRect.left = 0;
                outRect.right = space;
            } else if(position == 1 || position == 2 ) {
                outRect.top = 0;
                outRect.bottom = space;
                outRect.left = 0;
                outRect.right = space;
            } else if(position == 3 ) {
                outRect.top = 0;
                outRect.bottom = space;
                outRect.left = 0;
                outRect.right = 0;
            } else if(position == 4) {
                outRect.top = 0;
                outRect.bottom = 0;
                outRect.left = 0;
                outRect.right = space;
            } else if(position == 5 || position == 6) {
                outRect.top = 0;
                outRect.bottom = 0;
                outRect.left = 0;
                outRect.right = space;
            } else if(position == 7) {
                outRect.top = 0;
                outRect.bottom = 0;
                outRect.left = 0;
                outRect.right = 0;
            }
        }else if(item == Constant.TYPE_HOT_LIST_VIEW) {
            outRect.top = 0;
            if(position == 0){
                outRect.bottom = 0;
                outRect.left = 0;
                outRect.right = 0;
            } else {
                if(position % 2 == 1) {
                    outRect.left = space * 3;
                    outRect.right = space;
                } else {
                    outRect.left = space;
                    outRect.right = space * 3;
                }
                outRect.bottom = space * 3;
            }
        } else if(item == Constant.TYPE_HOME_FLASH_SALE) {
            itemCount = state.getItemCount()-1;
            outRect.top = 0;
            outRect.left = 0;
            outRect.right = 0;
            if(position == itemCount || position == itemCount -1) {
                outRect.bottom = 0;
            } else {
                outRect.bottom = space;
            }

        }
    }
}
