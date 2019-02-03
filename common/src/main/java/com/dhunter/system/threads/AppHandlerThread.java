package com.dhunter.system.threads;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by lgh on 2017/9/1.
 */

public class AppHandlerThread extends HandlerThread{
    // int mPriority;
    //int mTid = -1;
    // Looper mLooper;
    private String TAG = "AppHandlerThread";
    private Handler mHandler;

    public AppHandlerThread(String name)
    {
        super(name, android.os.Process.THREAD_PRIORITY_DEFAULT);
        //mPriority = android.os.Process.THREAD_PRIORITY_DEFAULT;
    }

    public AppHandlerThread(String name,int priority)
    {
        super(name,priority);
        //     this.mPriority = priority;
    }



    public synchronized void post(Runnable runnable) {
        if (mHandler == null) {
            mHandler = new Handler(getLooper());
        }
        mHandler.post(runnable);
    }

    public synchronized void postDelay(Runnable runnable, long delay) {
        if (mHandler == null) {
            mHandler = new Handler(getLooper());
        }
        mHandler.postDelayed(runnable, delay);
    }

    public synchronized Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(getLooper());
        }
        return mHandler;
    }
}
