package com.dhunter.system.eventCenter;

/**
 * Created by gohonglin on 2017/7/27.
 */

public interface IObserver {
    interface mainThread extends IObserver
    {
        void onEventMainThread(final Event event);
    }

    interface backgroundThread extends IObserver
    {
        void onEventBcakgroundThread(final Event event);
    }

    interface realTimeThread extends IObserver
    {
        void onEventRealTimeThread(final Event event);
    }
}
