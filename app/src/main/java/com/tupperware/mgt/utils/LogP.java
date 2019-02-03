package com.tupperware.mgt.utils;

import android.util.Log;

public class LogP {
    public static void i(String TAG,String message){
        Log.i(TAG,message);
    }

    public static void e(String TAG,String message){
        Log.e(TAG,message);
    }

    public static void d(String TAG,String message){
        Log.d(TAG,message);
    }

    public static void w(String TAG,String message){
        Log.w(TAG,message);
    }
}
