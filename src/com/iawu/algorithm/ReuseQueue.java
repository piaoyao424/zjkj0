package com.iawu.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 重用队列，自定义容量
 * @author sony
 *
 * @param <O>
 */
public class ReuseQueue <O>{
	
	private List<O> mlist;
	private Object lock;
	private int MaxSize;
	public ReuseQueue(int MaxSize)
	{
		this.MaxSize=MaxSize;
		lock=new Object();
		mlist=new ArrayList<O>();
	}
	public O Get()
	{
		synchronized (lock) {
			if(mlist.size()==0)
				return null;
			O obj=mlist.get(0);
			mlist.remove(0);
			return obj;
		}
	}
	public int GetSize()
	{
		return mlist.size();
	}
	public void Put(O obj)
	{
		synchronized (lock) {
			if(mlist.size()>=MaxSize)
				return;
			mlist.add(obj);
		}
	}
}
