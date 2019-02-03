package com.dhunter.common.network;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dhunter on 2018/6/25.
 */
@Singleton
public class DataBaseHelper {
    private Context context;
    @Inject
    public DataBaseHelper(Context context) {
        this.context = context;
    }
}