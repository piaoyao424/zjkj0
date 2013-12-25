package com.iawu.msgcenter;

import java.util.ArrayList;
import java.util.HashMap;

//减小类与类的耦合性,类与类间通过MsgCenter进行消息传递，不用直接引用其它类的方法
//对于界面类onCreate时Register 感兴趣的消息,onDestory 时UnRegister相应的消息
//比如，当底层出现网络错误时，MsgCenter.getInstance().postMsg(NetError,agr),
//如果某些界面在启动时MsgCenter.getInstance().register(NetError),
//那么这个界面就会收到此消息，并做出相应反应
//例如：
//MVQQMgr 是消息发送者，见OnMVQQNotifyUI
//SessionActivity 是消息接收者，见 RegisterMsg，UnRegisterMsg，doEvent




//这是个消息中心，对于消息发送者，直接postMsg,对于消息接收者，感兴趣的类注册感兴趣的msg,
public class MsgCenter {
	
	static final int MIN_EVENT=-100;
	static final int MAX_EVENT=100000;
	
	@SuppressWarnings("unused")
	private static final String TAG="EventCenter";
	
	HashMap<Integer, SingleEventHandler> singleEventMap;
	private ArrayList<RangeEventHandler> rangeEventList; 
	
	
	private MsgCenter()
	{
		singleEventMap=new HashMap<Integer, SingleEventHandler>();
		rangeEventList=new ArrayList<RangeEventHandler>();
		
	}
	private static MsgCenter mMsgCenter=new MsgCenter();
	
	public static MsgCenter getInstance(){
		return mMsgCenter;
	}

	//单独事件的listener ,比如注册事件5的listener,当其它类调用 postMsg(5)时，此listener能侦听得到
	public  void RegisterListener(int eventId, EventListener listener) {
		synchronized (singleEventMap) {
			SingleEventHandler cuHandler=singleEventMap.get(eventId);
			if(cuHandler==null)
			{
				cuHandler=new SingleEventHandler(eventId);
				singleEventMap.put(eventId, cuHandler);
			}			
			cuHandler.add(listener);
		}
		
	}
	//对于一个数组中的事件进行注册
	public  void RegisterListenerArray(int eventId[], EventListener listener) {
		if(eventId==null)
			return;
		for(int i=0;i<eventId.length;i++)
		{
			RegisterListener(eventId[i],listener);
		}
		
	}

	public  void UnRegisterListener(int eventId, EventListener listener) {
		synchronized (singleEventMap) {
			SingleEventHandler cuHandler=singleEventMap.get(eventId);
			if(cuHandler!=null)
			{
				cuHandler.remove(listener);
				if(cuHandler.isEmpty())
				{
					singleEventMap.remove(eventId);
				}
			}
		}
	}
	//对于一个数组中的事件进行注册
	public  void UnRegisterListenerArray(int eventId[], EventListener listener) {
		if(eventId==null)
			return;
		for(int i=0;i<eventId.length;i++)
		{
			UnRegisterListener(eventId[i],listener);
		}
		
	}
	
	
	//Range Listener,比如 注册5-9范围内的listen ,对于，5，6，7，8，9的事件都能侦听到
	public  void RegisterRangeListener(int minEventId,int maxEventId, EventListener listener) {
		
		synchronized (rangeEventList) { 
			RangeEventHandler cuHandler=null;
			for(int i=0;i<rangeEventList.size();i++)
			{
				if(rangeEventList.get(i).isMatch(minEventId,maxEventId))
				{
					cuHandler=rangeEventList.get(i);
					break;
				}
			}
			if(cuHandler==null)
			{
				cuHandler=new RangeEventHandler(minEventId,maxEventId);
				rangeEventList.add(cuHandler);
			}		
			cuHandler.add(listener);
		}	
	}
	
	public  void UnRegisterRangeListener(int minEventId,int maxEventId, EventListener listener) {
		synchronized (rangeEventList) {
			RangeEventHandler cuHandler=null;
			for(int i=0;i<rangeEventList.size();i++)
			{
				if(rangeEventList.get(i).isMatch(minEventId,maxEventId))
				{
					cuHandler=rangeEventList.get(i);
					break;
				}
			}
			if(cuHandler!=null)
			{
				cuHandler.remove(listener);
				if(cuHandler.isEmpty())
				{
					rangeEventList.remove(cuHandler);
				}
			}
		}
	}
	//所有事件，都会通知到这个listener
	public  void RegisterSuperListener(EventListener listener)
	{
		RegisterRangeListener(MIN_EVENT,MAX_EVENT,listener);
	}
	//所有事件，都会通知到这个listener
	public  void UnRegisterSuperListener(EventListener listener)
	{
		UnRegisterRangeListener(MIN_EVENT,MAX_EVENT,listener);
	}
	
	//发送一个事件msg,对于注册过的相关lister能够收到些消息，可多参数，参数的使用方法见EventArg
	public void PostMsg(int eventId,Object sender) {
		// 构造事件对象
		EventArg e = new EventArg();
		invoke(eventId,sender, e);
	}
	
	//发送一个事件msg,对于注册过的相关lister能够收到些消息，可多参数，参数的使用方法见EventArg
	public void PostMsg(int eventId,Object sender, Object... args) {
		// 构造事件对象
		EventArg e = new EventArg(args);
		invoke(eventId,sender, e);
	}
	
	
	private void invoke(int eventId,Object sender, EventArg e) {
		synchronized (singleEventMap) {
			// 单个事件lister的调用
			SingleEventHandler cuSingleHandler=singleEventMap.get(eventId);
			if(cuSingleHandler!=null)
			{
				cuSingleHandler.invoke(eventId,sender, e);
			}
		}
		synchronized (rangeEventList) {
		//范围lister的调用
		for(int i=0;i<rangeEventList.size();i++)
		{
			RangeEventHandler cuRangeEventHandler =rangeEventList.get(i);
			if(cuRangeEventHandler.needHandler(eventId))
			{
				cuRangeEventHandler.invoke(eventId, sender, e);
			}
		}
		}
	}
	
	
}
