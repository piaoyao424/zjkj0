package com.iawu.msgcenter;

import java.util.ArrayList;

import com.iawu.tools.Log;

public abstract class EventHandlerBase {
	private static final String TAG="EventCenter";
	
	private ArrayList<EventListener> listeners = new ArrayList<EventListener>();
	
	
	protected EventHandlerBase()
	{
		
	}

	public void add(EventListener listener) {
		// 判断在监听器列表中是否存在当前监听器对象
		synchronized (listeners) {
			if (listeners.indexOf(listener) < 0) {
				Log.d(TAG,"add:"+getLogName()+" listener:"+listener.toString()+" size:"+listeners.size());
				listeners.add(listener);
				
			}
		}
	}

	public void remove(EventListener listener) {
		synchronized (listeners) {
			Log.d(TAG,"remove:"+getLogName()+" listener:"+listener.toString()+" size is"+listeners.size());
			listeners.remove(listener);
		}
	}


	public void invoke(int eventId,Object sender, EventArg e) {
		synchronized (listeners) {
			// 构造事件对象
			for (int i = 0; i < listeners.size(); i++) {
				Log.d(TAG,"invoke:"+getLogName()+" listener:"+listeners.get(i).toString()+" size is"+listeners.size());
				listeners.get(i).doEvent(eventId,sender, e);
			}
		}
	}
	
	public boolean isEmpty()
	{
		return (listeners.size()<=0);
	}
	
	public abstract boolean needHandler(int event);
	
	public abstract String getLogName();
	
}
