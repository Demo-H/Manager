package com.dhunter.system.threads;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gohonglin on 2017/7/27.
 */

public class LinkQueueThreadFactory implements ThreadFactory {
    private final String mThreadName;
    private final int mPriority;
    private final AtomicInteger mThreadNumber = new AtomicInteger();
    private final String FLAG_THREADPOOL_THREAD =":stub";
    public LinkQueueThreadFactory(String threadName,int priority)
    {
        mThreadName = threadName;
        mPriority = priority;
    }
    @Override
    public Thread newThread(Runnable r) {
        // TODO Auto-generated method stub
        return new Thread(r,mThreadName+"-"+mThreadNumber.getAndIncrement()+FLAG_THREADPOOL_THREAD){
            public void run() {
                android.os.Process.setThreadPriority(mPriority);
                super.run();
            }
        };
    }

}
