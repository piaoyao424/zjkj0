package com.iawu.msgcenter;

public class SingleEventHandler  extends EventHandlerBase{

	//避免上层调用这个函数
	@SuppressWarnings("unused")
	private SingleEventHandler()
	{
		super();
	}
	private int mEventId;
	
	public SingleEventHandler(int eventId)
	{
		mEventId=eventId;
	}

	@Override
	public boolean needHandler(int event) {
		if(mEventId==event)
		{
			return true;
		}
		return false;
	}
	@Override
	public String getLogName() {
		String name=""+mEventId+":";
		return name;
	}
	
}
