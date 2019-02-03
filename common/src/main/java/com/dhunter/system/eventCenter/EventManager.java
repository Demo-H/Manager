package com.dhunter.system.eventCenter;

import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by gohonglin on 2017/7/27.
 */

public class EventManager {
    public static EventManager mInstance = null;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    //private Map<EventSource,SparseArray<ObserverBean>> observerMap = new ConcurrentHashMap<EventSource,SparseArray<ObserverBean>>();
    private Map<String,ArrayList<ObserverBean>> observerMap = new ConcurrentHashMap<String,ArrayList<ObserverBean>>();
    public static EventManager getInstance()
    {
        if(mInstance == null)
        {
            synchronized(EventManager.class)
            {
                mInstance = new EventManager();
            }
        }
        return mInstance;
    }

    private EventManager()
    {

    }

    public void addObserver(IObserver observer,String eventSourceName,ThreadMode threadMode,int... whats)
    {
        if(observer == null) {
            throw new NullPointerException("observingObject must not be null");

        }

        if(eventSourceName == null)
        {
            throw new NullPointerException("eventSourceName must not be null");
        }

        if(whats == null)
        {
            throw new NullPointerException("whats must not be null");
        }

        EventSource source=  new EventSource(eventSourceName);

        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            ObserverBean observerBean = new ObserverBean(this,threadMode,source.getSender(),observer,whats);
            ArrayList<ObserverBean> observers = observerMap.get(source.getName());
            if(observers == null)
            {
                observers = new ArrayList<ObserverBean>();
                observerMap.put(source.getName(),observers);
            }

            observers.add(observerBean);

        }finally {
            writeLock.unlock();
        }
    }

    public void removeOberser(IObserver observer,String eventSourceName) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        ArrayList<ObserverBean> observers = observerMap.get(eventSourceName);
        if(observers == null || observers.size() == 0) {
            writeLock.unlock();
            return;
        }
        for(ObserverBean bean : observers) {
            if(bean.getObserverObject() == observer) {
                observers.remove(bean);
                break;
            }
        }
        writeLock.unlock();
    }

    public void postEvent(EventSource eventSource ,int what) {
        postEvent(eventSource,what, new Object[0]);
    }

    public void postEvent(String sourceName ,int what) {
        postEvent(new EventSource(sourceName),what, new Object[0]);
    }

    public void postEvent(String sourceName ,int what, Object... params) {
        postEvent(new EventSource(sourceName),what, params);
    }

    public void postEvent(EventSource eventSource,int what, Object... params) {
        postEvent(eventSource,what, Event.EventRank.NORMAL, params);
    }

    public void postEvent(EventSource eventSource,int what,Event.EventRank eventRank, Object... params) {
        Event event = new Event(what,eventSource,params,eventRank);
        postEvent(event);
    }

    public void postEvent(final Event event) {
        if(event == null)
        {
            throw new NullPointerException("event must not be null");
        }

        EventSource source = event.source;

        if(source == null)
        {
            throw new NullPointerException("eventsouce can not be null");
        }

        boolean currentIsMainThread = Looper.myLooper() == Looper.getMainLooper();
        postSingleEvent(event,currentIsMainThread);

    }

    private void postSingleEvent(final Event event,boolean currentIsMainThread)
    {
        Log.i("LGHMusic","postSingleEvent,event.what = "+event.what);
        ArrayList<ObserverBean> observers = observerMap.get(event.source.getName());
        if(observers!=null)
        {
            for(ObserverBean observer:observers)
            {
                for(int what:observer.eventWhats)
                {
                    if(what == event.what)
                        observer.dispatchEvent(event);
                }
            }
        }
    }

    private Collection<ObserverBean> getObserverBeans(Event event)
    {
        final String sourceName = event.source.getName();
        ArrayList<ObserverBean> observers = observerMap.get(sourceName);
        return observers;
    }


}
