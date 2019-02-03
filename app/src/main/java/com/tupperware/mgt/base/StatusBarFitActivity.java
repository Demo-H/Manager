package com.tupperware.mgt.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tupperware.mgt.utils.StatusBarUtil;


/**
 * Created by lgh on 2018/10/9.
 */

public abstract class StatusBarFitActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        StatusBarUtil.setGeneralStatusTheme(this);
    }
}
