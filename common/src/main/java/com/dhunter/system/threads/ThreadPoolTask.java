package com.dhunter.system.threads;

/**
 * Created by gohonglin on 2017/7/27.
 */

public class ThreadPoolTask implements Runnable{
    private Runnable mJob;
    private ThreadPoolTaskListenerImpl mListener;
    @Override
    public void run() {
        // TODO Auto-generated method stub
        if(mListener!=null)
            mListener.onTaskBegin();
        mJob.run();
        if(mListener!=null)
            mListener.onTaskDone();

    }

    public ThreadPoolTask(Runnable job,ThreadPoolTaskListenerImpl listener)
    {
        mJob = job;
        mListener = listener;
    }
}
