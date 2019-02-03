package com.dhunter.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Dhunter on 2018/11/5.
 */
public class WrapContentHeightListView extends ListView {
    public WrapContentHeightListView(Context context) {
        super(context);
    }

    public WrapContentHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            if (h > height) height = h;
//        }
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
