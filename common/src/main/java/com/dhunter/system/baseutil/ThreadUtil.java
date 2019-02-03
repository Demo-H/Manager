package com.dhunter.system.baseutil;

import android.os.Looper;

import com.dhunter.system.threads.HandlerThreadFactory;


/**
 * Created by gohonglin on 2017/7/27.
 */

public class ThreadUtil {
    public static void postToUIThread(Runnable r)
    {
        if (Thread.currentThread() == Looper.getMainLooper().getThread())
            r.run();
        else
        {
            HandlerThreadFactory.getMainThreadHandler().post(r);
        }
    }

    public static void PostToRealtimeThread(Runnable r)
    {
        if (Thread.currentThread() == HandlerThreadFactory.getHandlerThread(HandlerThreadFactory.RealTimeThread))
            r.run();
        else
        {
            HandlerThreadFactory.getHandlerThread(HandlerThreadFactory.RealTimeThread).getHandler().post(r);
        }
    }

    public static void PostToBackgroundThread(Runnable r)
    {
        if (Thread.currentThread() == HandlerThreadFactory.getHandlerThread(HandlerThreadFactory.BackgroundThread))
            r.run();
        else
        {
            HandlerThreadFactory.getHandlerThread(HandlerThreadFactory.BackgroundThread).getHandler().post(r);
        }
    }
}
