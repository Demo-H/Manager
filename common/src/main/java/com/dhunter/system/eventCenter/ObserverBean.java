package com.dhunter.system.eventCenter;


import com.dhunter.system.baseutil.ThreadUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by gohonglin on 2017/7/27.
 */

public class ObserverBean {
    private EventManager eventManager;
    public final ThreadMode mThreadMode;
    private final WeakReference<Object> observingObjectReference;
    private final WeakReference<Object> eventSourceSenderReference;
    public ArrayList<Integer> eventWhats = new ArrayList<Integer>();

    public ObserverBean(EventManager em,ThreadMode threadMode,Object sender,Object observeringObject,int... whats)
    {
        if(observeringObject == null)
        {
            throw new NullPointerException("observeringObject is null");
        }
        this.eventManager = em;
        this.mThreadMode = threadMode;
        this.observingObjectReference = new WeakReference<Object>(observeringObject);
        this.eventSourceSenderReference = new WeakReference<Object>(sender);
        initData(whats);
    }

    private void initData(int... whats)
    {
        for(int what:whats)
        {
            eventWhats.add(what);
        }
    }

    public Object getObserverObject()
    {
        return observingObjectReference.get();
    }

    public Object getSourceSenderObject()
    {
        return eventSourceSenderReference.get();
    }

    public void dispatchEvent(final Event event)
    {
        final Object observerObject = getObserverObject();
        if(observerObject == null || !(observerObject instanceof IObserver) )
            return;
        switch(mThreadMode)
        {
            case MainThread:

                ThreadUtil.postToUIThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        ((IObserver.mainThread)observerObject).onEventMainThread(event);
                    }

                });
                break;

            case BackgroundThread:

                ThreadUtil.PostToBackgroundThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        ((IObserver.backgroundThread)observerObject).onEventBcakgroundThread(event);
                    }

                });

                break;

            case RealtimeThread:

                ThreadUtil.PostToRealtimeThread(new Runnable(){
                    @Override
                    public void run() {
                        ((IObserver.realTimeThread)observerObject).onEventRealTimeThread(event);
                    }
                });

                break;


        }

    }


}
