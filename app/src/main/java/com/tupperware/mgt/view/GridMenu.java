package com.tupperware.mgt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhunter.common.utils.DisplayUtils;
import com.tupperware.mgt.R;


/**
 * Created by dhunter on 2018/3/12.
 */

public class GridMenu extends LinearLayout {
    private ImageView iv_menu;
    private TextView tv_menu;

    public GridMenu(Context context) {
        super(context);
        init(context);
    }



    public GridMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public GridMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }
    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);
        setPadding(DisplayUtils.dip2px(context,5),DisplayUtils.dip2px(context, 5),DisplayUtils.dip2px(context, 5),DisplayUtils.dip2px(context, 5));
//        Log.d("alan","初始化");
        View menu = LayoutInflater.from(context).inflate(R.layout.view_gride_menu, this, true);
        iv_menu = (ImageView) menu.findViewById(R.id.iv_menu);
        tv_menu = (TextView) menu.findViewById(R.id.tv_menu);
    }

    public void setAttr(int imgRes, String txt){
        iv_menu.setImageResource(imgRes);
        tv_menu.setText(txt);
    }
}
