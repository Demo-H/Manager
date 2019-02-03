package com.dhunter.system.threads;

import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gohonglin on 2017/7/27.
 */


public class HandlerThreadFactory {
    private String TAG = "HandlerThreadFactory";
    public static final String BackgroundThread = "XHR_Background_HandlerThread";
    public static final String RealTimeThread = "XHR_RealTime_HandlerThread";
    public static final String TimerThread = "XHR_timer_HandlerThread";
    private static Map<String,AppHandlerThread> mHandlerThread = new HashMap<String,AppHandlerThread>();
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    public static AppHandlerThread getHandlerThread(String ThreadName)
    {
        AppHandlerThread thread = mHandlerThread.get(ThreadName);
        if(null == thread)
        {
            thread = new AppHandlerThread(ThreadName,getThreadPriority(ThreadName));
            thread.start();
            mHandlerThread.put(ThreadName,thread);
        }else{
            if(!thread.isAlive())
                thread.start();
        }

        return thread;
    }


    private static int getThreadPriority(String threadName)
    {
        if(BackgroundThread.equals(threadName))
            return android.os.Process.THREAD_PRIORITY_BACKGROUND;
        else if(RealTimeThread.equals(threadName))
            return android.os.Process.THREAD_PRIORITY_FOREGROUND;
        else
            return android.os.Process.THREAD_PRIORITY_DEFAULT;
    }

    public static Handler getMainThreadHandler()
    {
        return mainThreadHandler;
    }
}
