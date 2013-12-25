package com.iawu.model;


import org.json.JSONObject;

public abstract class BaseJsonItem {
	public BaseJsonItem()
	{
		
	}
	public int status;
	public String info;
	
	public abstract  boolean CreateFromJson(JSONObject result);
	
}
