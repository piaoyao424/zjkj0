package com.iawu.msgcenter;

//这个会
public class RangeEventHandler extends EventHandlerBase {
	//避免上层调用这个函数
	@SuppressWarnings("unused")
	private RangeEventHandler()
	{
		super();
	}
	private int mMinEvent;
	private int mMaxEvent;
	
	public RangeEventHandler(int minEvent,int maxEvent )
	{
		mMinEvent=minEvent;
		mMaxEvent=maxEvent;
	}
 
	
	@Override
	public boolean needHandler(int event) {
		if(event>=mMinEvent&&event<=mMaxEvent)
		{
			return true;
		}
		return false;
	}
	
	public boolean isMatch(int minEvent,int maxEvent)
	{
		return (mMinEvent==minEvent&&mMaxEvent==maxEvent);
	}


	@Override
	public String getLogName() {
		String name="("+mMinEvent+"-"+mMaxEvent+")";
		return name;
	}
	
	
}
