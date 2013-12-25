package com.iawu.tools;

import org.json.JSONException;
import org.json.JSONObject;
//此类避免JSON值为null造成的解析失败
public class CommonConvert {
	JSONObject obj=new JSONObject();
	public CommonConvert(JSONObject obj){
		this.obj=obj;
	}
	public String getString(String key) throws JSONException{
		if(obj.isNull(key)) return "";
		else if(obj.getString(key)==null||obj.getString(key)=="null") return "";
		else
		{
			
			 String ret= obj.getString(key);
			 return Util.unescape(ret);
		}

	}
	public int getInt(String key) throws JSONException{
		if(obj.isNull(key)) return 0;
		else return obj.getInt(key);
	}
	public long getLong(String key) throws JSONException{
		if(obj.isNull(key)) return 0;
		else return obj.getLong(key);
	}
	public double getDouble(String key) throws JSONException{
		if(obj.isNull(key)) return 0.0;
		else return obj.getDouble(key);
	}

}
