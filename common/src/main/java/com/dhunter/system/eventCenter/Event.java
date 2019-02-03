package com.dhunter.system.eventCenter;

/**
 * Created by gohonglin on 2017/7/27.
 */

public class Event {
    public int what;
    public EventSource source;
    public Object params;
    public EventRank eventRank;

    public static enum EventRank
    {
        NORMAL,
        SYSTEM,
        CORE
    }

    public Event(int what , EventSource source,Object params,EventRank eventRank)
    {
        this.what = what;
        this.source = source;
        this.params = params;
        this.eventRank = eventRank;
    }

    public Event(int what , EventSource source,EventRank eventRank)
    {
        this(what,source,null,eventRank);
    }

    public Event(int what,EventSource source)
    {
        this(what,source,null, EventRank.NORMAL);
    }
}
