package com.dhunter.system.eventCenter;

/**
 * Created by gohonglin on 2017/7/27.
 */

public class EventSource {

    private String name;
    private Object eventSender;

    public EventSource(String name,Object sender)
    {
        this.name = name;
        this.eventSender = sender;
    }

    public EventSource(String name)
    {
        this(name,null);
    }

    public String getName()
    {
        return name;
    }

    public Object getSender()
    {
        return eventSender;
    }

    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(getClass() != obj.getClass())
            return false;
        EventSource objSource = (EventSource)obj;
        if(name==null && objSource.getName()!=null)
            return false;
        else if(!name.equals(objSource.getName()))
            return false;
        return true;
    }
}
