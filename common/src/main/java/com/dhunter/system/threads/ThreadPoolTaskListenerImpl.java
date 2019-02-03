package com.dhunter.system.threads;

/**
 * Created by gohonglin on 2017/7/27.
 */

public interface ThreadPoolTaskListenerImpl {
    public void onTaskBegin();
    public void onTaskDone();
}
