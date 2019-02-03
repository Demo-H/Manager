package com.dhunter.system.threads;


import com.dhunter.system.baseutil.SystemUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by gohonglin on 2017/7/27.
 */

public class ThreadPoolManager {
    private static ThreadPoolManager mInstance;
    private final Executor mExecutor;
    private int CORE_POOL_SIZE;
    private int MAXIMUM_POOL_SIZE;
    private int KEEP_ALIVE_TIME = 5;

    public static ThreadPoolManager getInstance()
    {
        if(mInstance == null)
        {
            synchronized(ThreadPoolManager.class)
            {
                mInstance = new ThreadPoolManager();
            }
        }
        return mInstance;
    }

    private void initData()
    {
        CORE_POOL_SIZE = SystemUtil.getCpuCoresNum();
        if(CORE_POOL_SIZE == 0)
            CORE_POOL_SIZE = 2;
        MAXIMUM_POOL_SIZE = 2*CORE_POOL_SIZE;
        KEEP_ALIVE_TIME = 5;
    }
    private ThreadPoolManager()
    {
        initData();
        mExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(),
                new LinkQueueThreadFactory("AppThreadPoolThread",android.os.Process.THREAD_PRIORITY_BACKGROUND));
    }

    /*
    * r:�̳߳�Ҫִ�е�����
    * ���ܣ��ύ�����̳߳�
    * **/
    public void submit(Runnable r)
    {
        submit(r,null);
    }

    /*
    * r:�̳߳�Ҫִ�е�����
    * listener���̳߳�ִ������ʱ��ļ���
    *  ���ܣ��ύ�����̳߳�
    * **/
    public void submit(Runnable r,ThreadPoolTaskListenerImpl listener)
    {
        mExecutor.execute(new ThreadPoolTask(r,listener));
    }
}
