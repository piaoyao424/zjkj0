package com.iawu.xq;

import java.util.HashMap;

public class ClassTools {
	static public final String GRAB_RESULT_LISTNER = "DriverGrabActivity";
	static public final String NEAR_BY_DRIVER_POSITION = "CallTaxiView";
	static public boolean isRequesting = false; 
	
	private HashMap<String, Object> data;
	
	private ClassTools(){
		 data = new HashMap<String, Object>();
	}
	
	private static ClassTools instance = new ClassTools();
	
	static public ClassTools getInstance(){
		return instance;
	}
	
	public void put(String k, Object v){
		if (data.containsKey(k))
			data.remove(k);
		data.put(k, v);
	}
	
	public Object get(String k){
		return data.get(k);
	}
	
	public void clear(){
		data.clear();
	}
	
	public boolean containsKey(String k){
		return data.containsKey(k);
	}
	
	public void remove(String k){
		data.remove(k);
	}
	
}
